package com.jian.core.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jian.core.model.bean.User;
import com.jian.core.model.bean.UserAttention;
import com.jian.core.model.bo.UserAttentionBo;
import com.jian.core.model.util.ImgUtil;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.redis.util.RedisUtil2;
import com.jian.core.server.dao.UserAttentionDao;
import com.jian.core.server.service.LoginService;
import com.jian.core.server.service.UserAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.jian.core.model.bean.inter.Constant.REDIS_VALID_USER_KEY;
import static com.jian.core.model.bean.inter.ImgUrls.HADE_IMG_URL_PATH;


@SuppressWarnings("ALL")
@Component
@Transactional
public class UserAttentionServiceImpl implements UserAttentionService {
	@Autowired
	private UserAttentionDao userAttentionDao;

	@Autowired
	private RedisUtil2 redisUtil2;

	@Autowired
    private LoginService loginService;

	@Autowired
    private RedisUtil redisUtil;

	@Override
	public UserAttention selectByPrimaryKey(Integer sid) {
		return userAttentionDao.selectByPrimaryKey(sid);
	}

	@Override
	public int deleteByPrimaryKey(Integer sid) {
		return userAttentionDao.deleteByPrimaryKey(sid);
	}

	@Override
	public int insert(UserAttention record) {
		return userAttentionDao.insert(record);
	}

	@Override
	public int updateByPrimaryKey(UserAttention record) {
		return userAttentionDao.updateByPrimaryKey(record);
	}

	//获取关系
	//关系==异常:-1, 无关系:0, 关注:1, 被关注(粉丝):2, 互相关注:3, 自己:99
	public int getRelation(Integer userId,Integer followUserId){
		int rint = -1;
		List<UserAttention> userAttentionList = new ArrayList<>();
		userAttentionList = rSelectByAttention(userId,followUserId);
		if(userAttentionList.isEmpty()){
			userAttentionList = userAttentionDao.selectByAttention(userId,followUserId);
		}
		if(userAttentionList.size()>1) {//互相关注
			rint = 3;
		}else if(userAttentionList.size()==1){
			UserAttention userAttention = userAttentionList.get(0);
			if(userId.equals(userAttention.getUserId())) {//关注
				rint = 1;
			}else if(userId.equals(userAttention.getFollowUserId())) {//粉丝
				rint = 2;
			}
		}else {//无关系
			rint = 0;
		}
		if (userId.equals(followUserId)){
		    rint = 99;//自己
        }
		return rint;
	}

	//执行操作后的现任状态
	//异常:-1, 无关系:0, 关注:1, 被关注(粉丝):2, 互相关注:3
	@Override
	public int payAttention(Integer userId,Integer followUserId) {
		int rint = -1;
		List<UserAttention> userAttentionList = new ArrayList<>();
		userAttentionList = rSelectByAttention(userId,followUserId);
		if(userAttentionList.isEmpty()){
			userAttentionList = userAttentionDao.selectByAttention(userId,followUserId);
		}
		UserAttention userAttention = new UserAttention();
		if(userAttentionList.size()>1){//互相关注
			for(UserAttention userAttentionPer:userAttentionList){
				if(userId.equals(userAttentionPer.getUserId())){
					rint = userAttentionDao.deleteByPrimaryKey(userAttentionPer.getSid());
					break;
				}
			}
			if(rint==1){
				redisUtil2.cancelAttention(userId,followUserId);
				rint = 2;//互相关注,变成粉丝
			}
		}else if(userAttentionList.size()==1){
			userAttention = userAttentionList.get(0);
			if(userId.equals(userAttention.getUserId())){//关注
				rint = userAttentionDao.deleteByPrimaryKey(userAttention.getSid());
				if(rint==1){
					redisUtil2.cancelAttention(userId,followUserId);
					rint = 0;//关注，变成无关系
				}
			}else if(userId.equals(userAttention.getFollowUserId())){//粉丝
				userAttention = new UserAttention();
				userAttention.setUserId(userId);
				userAttention.setFollowUserId(followUserId);
				userAttention.setAddDate(new Date());
				rint = userAttentionDao.insert(userAttention);
				if(rint==1){
					redisUtil2.payAttention(userAttention.getUserId(),userAttention.getFollowUserId(),userAttention.getSid());
					rint = 3;//粉丝，变成互相关注
				}
			}
		}else{//无关系
			userAttention = new UserAttention();
			userAttention.setUserId(userId);
			userAttention.setFollowUserId(followUserId);
			userAttention.setAddDate(new Date());
			rint = userAttentionDao.insert(userAttention);
			if(rint==1){
				redisUtil2.payAttention(userAttention.getUserId(),userAttention.getFollowUserId(),userAttention.getSid());
			}
		}
		return rint;
	}

	public int getFollow(Integer userId){
		int rint = 0;
		rint = redisUtil2.getFollow(userId).intValue();
		if(rint<1) {
			rint = userAttentionDao.getFollow(userId);
		}
		return rint;
	}

    public int getFans(Integer userId){
		int rint = 0;
		rint = redisUtil2.getFans(userId).intValue();
		if(rint<1) {
			rint = userAttentionDao.getFans(userId);
		}
	    return rint;
    }

	//获取关注和粉丝
	public List<UserAttention> rSelectByAttention(Integer userId, Integer followUserId) {
		List<UserAttention> rList = new ArrayList<>();
		Double follow = redisUtil2.zScore(redisUtil2.ATTENTION + ":" + (userId.toString()) + ":" + redisUtil2.ATTENTION_FOLLOW, (followUserId.toString()));
		if (follow != null) {
			UserAttention userAttention = new UserAttention();
			userAttention.setSid(follow.intValue());
			userAttention.setUserId(userId);
			userAttention.setFollowUserId(followUserId);
			rList.add(userAttention);
		}
		Double fans = redisUtil2.zScore(redisUtil2.ATTENTION + ":" + (userId.toString()) + ":" + redisUtil2.ATTENTION_FANS, (followUserId.toString()));
		if (fans != null) {
			UserAttention userAttention = new UserAttention();
			userAttention.setSid(fans.intValue());
			userAttention.setUserId(followUserId);
			userAttention.setFollowUserId(userId);
			rList.add(userAttention);
		}
		return rList;
	}


	public List<UserAttentionBo> getAttentionList(Integer userId, Integer type, long start, long end){
		Set<Object> otherUserIds = new HashSet<>();
		switch (type){
			case 1://关注人列表
				otherUserIds = redisUtil2.getFollowRange(userId,start,end);
				break;
			case 2://粉丝列表
				otherUserIds = redisUtil2.getFansRange(userId,start,end);
				break;
			default:
		}
		List<UserAttentionBo> attentionList = new ArrayList<>();
		for(Object otherUserId:otherUserIds){
			User user = JSONObject.parseObject(redisUtil.getHashValue(REDIS_VALID_USER_KEY,(String)otherUserId),User.class);
			if(user!=null){
				UserAttentionBo userAttentionBo = new UserAttentionBo();
				userAttentionBo.setSid(user.getUserId());
				userAttentionBo.setUserNickName(user.getName());
				userAttentionBo.setHeadImgUrl(ImgUtil.getImgPath(user.getPhoto(),HADE_IMG_URL_PATH));
				userAttentionBo.setRelation(getRelation(userId,Integer.parseInt((String)otherUserId)));
				attentionList.add(userAttentionBo);
			}
		}
		return attentionList;
	}

	@Override
	public List<UserAttentionBo> getUserFansList(int LogigUserId, int fromUserId, long start, long end,int type) {
		Set<Object> otherUserIds = new HashSet<>();
		if (type == 1){
			otherUserIds = redisUtil2.getFollowRange(fromUserId,start,end);
		}if (type ==2){
			otherUserIds=redisUtil2.getFansRange(fromUserId,start,end);
		}

		List<UserAttentionBo> attentionList = new ArrayList<>();
		for(Object otherUserId:otherUserIds){
			User user = JSONObject.parseObject(redisUtil.getHashValue(REDIS_VALID_USER_KEY,(String)otherUserId),User.class);
			if(user!=null){
				UserAttentionBo userAttentionBo = new UserAttentionBo();
                userAttentionBo.setSid(user.getUserId());
                userAttentionBo.setUserNickName(user.getName());
                userAttentionBo.setHeadImgUrl(ImgUtil.getImgPath(user.getPhoto(),HADE_IMG_URL_PATH));
				userAttentionBo.setRelation(getRelation(LogigUserId,Integer.parseInt((String)otherUserId)));
				attentionList.add(userAttentionBo);
			}
		}
		return attentionList;
	}

    @Override
    public Set<Object> getAttention(int userId) {
        Set<Object> UserIds = new HashSet<>();
        UserIds =redisUtil2.getFollowRange(userId,0,-1);
        return UserIds;
    }

    @Override
    public Set<Object> getUserFans(int userId) {
        Set<Object> UserIds = new HashSet<>();
        UserIds=redisUtil2.getFansRange(userId,0,-1);
        return UserIds;
    }
}
