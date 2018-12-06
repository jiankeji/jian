package com.jian.core.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.jian.core.model.bean.Comment;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.model.bean.Img;
import com.jian.core.model.bean.Reply;
import com.jian.core.model.util.PropertiesUtil;
import com.jian.core.model.util.UploadFile;
import com.jian.core.redis.util.RedisUtil2;
import com.jian.core.server.dao.UserDynamicDao;
import com.jian.core.server.redisDao.MqDynamicDao;
import com.jian.core.server.redisDao.UserDynamicRedisDao;
import com.jian.core.server.redisDao.UserRedisDao;
import com.jian.core.server.service.UserDynamicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.jian.core.model.bean.inter.ImgUrls.IMGSRC;

/**
 * 用户动态 评论 回复 点赞 service实现层
 * @author shen
 *
 */
@Service
@Transactional
public class UserDynamicServiceImpl implements UserDynamicService {

	@Resource
	private UserDynamicDao userDynamicDao;

	@Resource
	private UserDynamicRedisDao userDynamicRedisDao;

	@Resource
	private UserRedisDao userRedisDao;

	@Resource
	private MqDynamicDao mqDynamicDao;

	@Resource
	private RedisUtil2 redisUtil2;
	@Override
	public Dynamic publish(String token, String content) {
		Integer  userId = userRedisDao.getUserId(token);

		Dynamic dynamic = new Dynamic();
		dynamic.setContent(content);
		dynamic.setBrowse(0);
		dynamic.setCreateTime(System.currentTimeMillis());
		dynamic.setLikenum(0);
		dynamic.setUserId(userId);
		userDynamicDao.addDynamic(dynamic);
		mqDynamicDao.sendDynamicFans(dynamic);
		return dynamic;
	}

	@Override
	public Img publishimg(String paths,Integer dynamicId) {

		Img img = new Img();
		img.setDynamicId(dynamicId);
		img.setPhoto(paths);
		userDynamicDao.addDynamicImg(img);
		return img;
	}

	@Override
	public int publistRedis(Dynamic dynamic) {
		userDynamicRedisDao.insertDynamic(dynamic);
		return 0;
	}

	@Override
	public int like(Integer dynamicId, Integer userId) {
		int likeStatus;
		try {
			likeStatus = userDynamicDao.selectLikeStatus(dynamicId, userId);
			if(likeStatus == 0) {
				userDynamicDao.updateLikeStatus(dynamicId, userId, 1);
				userDynamicDao.updateLikeNum(dynamicId, 1);
				userDynamicRedisDao.dynamicLike(dynamicId, userId);
			}
			if(likeStatus == 1) {
				userDynamicDao.updateLikeStatus(dynamicId, userId, 0);
				userDynamicDao.updateLikeNum(dynamicId, -1);
				userDynamicRedisDao.cancelDynamicLike(dynamicId, userId);
			}
		} catch (Exception e) {
			likeStatus = -1;
		}
		if(likeStatus == -1) {//点赞
			userDynamicDao.addUserLike(dynamicId, userId);
			userDynamicDao.updateLikeNum(dynamicId, 1);
			userDynamicRedisDao.dynamicLike(dynamicId, userId);
		}
		int likenum = userDynamicDao.selectLikeNum(dynamicId);
		return likenum;
	}


	@Override
	public List<Dynamic> showDynamic(Integer pageNum, Integer pageSize,Integer userId,Integer status) {

	    List<Dynamic> list = userDynamicRedisDao.showDynamic(pageNum,pageSize,userId,status);
	    if(list!=null&&!list.isEmpty()){
	    	for(Dynamic d:list){
				userDynamicDao.browse(d.getDynamicId());
			}

	    	return  list;
	    }

		List<Dynamic> dmcList;
		if(status==0){
			PageHelper.startPage(pageNum, pageSize);
			dmcList = userDynamicDao.showDynamic();
		}else{
			PageHelper.startPage(pageNum, pageSize);
			dmcList = userDynamicDao.showDynamicByUserId(userId);
		}
		if(dmcList==null||dmcList.size()==0){
			return new ArrayList<>();
		}

		for(Dynamic dmc:dmcList) {
			List<Img> imgList = userDynamicDao.selectDMCIMGByDMC(dmc.getDynamicId());
			dmc.setImgList(imgList);
			List<Comment> commentList= userDynamicDao.selectCommentbyDMC(dmc.getDynamicId());
			dmc.setCommentList(commentList);
			for(Comment cmt:commentList) {
				List<Reply> replyList = userDynamicDao.selectReplyByCMT(cmt.getCommentId());
				cmt.setReplyList(replyList);
			}
		}
		return dmcList;
	}

	@Override
	public int comment(Comment comment, String token) {
		Integer  userId = userRedisDao.getUserId(token);
		comment.setCommentTime(System.currentTimeMillis());
		comment.setLikeNum(0);
		comment.setFromUserId(userId);
		userDynamicDao.comment(comment);
		userDynamicRedisDao.comment(comment);
		return 0;
	}

	@Override
	public int reply(Reply reply, String token) {
		Integer  userId = userRedisDao.getUserId(token);
		reply.setReplyTime(System.currentTimeMillis());
		reply.setFromUserId(userId);
		userDynamicDao.reply(reply);
		userDynamicRedisDao.reply(reply);
		return 0;
	}

	@Override
	public int deleteReply(Integer replyId,Integer commentId, String token) {
		Integer  userId = userRedisDao.getUserId(token);
		int status ;
		try {
			status= userDynamicDao.selectReplyById(replyId, userId);
			userDynamicDao.deleteReply(replyId);
		} catch (Exception e) {
			status = 203;
		}
		userDynamicRedisDao.deleteReply(replyId,commentId);
		return status;
	}

	@Override
	public int deleteComment(Integer commentId,Integer dynamicId, String token) {
		Integer  userId = userRedisDao.getUserId(token);
		int status ;
		try {
			status = userDynamicDao.selectCommentById(commentId, userId);
			List<Reply> replyList = userDynamicDao.selectReplyByCMT(commentId);
			for(Reply reply:replyList) {
				userDynamicDao.deleteReply(reply.getReplyId());
			}
			userDynamicDao.deleteComment(commentId);
			userDynamicRedisDao.deleteComment(commentId,dynamicId);
		} catch (Exception e) {
			status = 203;
		}

		return status;
	}

	@Override
	public int deleteDynamic(Integer dynamicId, String token) {
		Integer  userId = userRedisDao.getUserId(token);
		String imgPath = PropertiesUtil.getProperty(IMGSRC);
		int status;
		try {
			status = userDynamicDao.selectDynamicById(dynamicId, userId);
			List<Img> imgList = userDynamicDao.selectDMCIMGByDMC(dynamicId);
			for(Img img:imgList) {
				userDynamicDao.deleteImgById(img.getImgId());
				String path = img.getPhoto();
				String paths = imgPath+path;
				UploadFile.deleteServerFile(paths);
			}
			List<Comment> cmtList = userDynamicDao.selectCommentbyDMC(dynamicId);
			for(Comment cmt:cmtList) {
				List<Reply> replyList = userDynamicDao.selectReplyByCMT(cmt.getCommentId());
				for(Reply re: replyList) {
					userDynamicDao.deleteReply(re.getReplyId());
				}
				userDynamicDao.deleteComment(cmt.getCommentId());
			}
			userDynamicDao.deleteDynamicById(dynamicId);
			userDynamicRedisDao.deleteDynamic(dynamicId);
		} catch (Exception e) {
			e.printStackTrace();
			status=203;
		}


		return status;
	}

	@Override
	public void browse(Integer dynamicId) {
		userDynamicDao.browse(dynamicId);
		userDynamicRedisDao.browse(dynamicId);
	}

	@Override
	public void commentLike(Integer commentId,String token) {
		Integer  userId = userRedisDao.getUserId(token);

		int status;
		try {
			status= userDynamicDao.getCommentLikeStatus(commentId,userId);
		}catch (Exception e){
			status=-1;
		}
		Integer dynamicId = userDynamicDao.getDynamicIdByCommentId(commentId);
		if(status==-1){//现在点赞
			userDynamicDao.saveCommentLike(commentId,userId,1);
			userDynamicDao.updateCommentLikenum(commentId,1);
			userDynamicRedisDao.commentLike(commentId,dynamicId,1);
		}
		if(status==0){//现在点赞  以前点过 后面取消了
			userDynamicDao.updateCommentLike(commentId,userId,1);
			userDynamicDao.updateCommentLikenum(commentId,1);
			userDynamicRedisDao.commentLike(commentId,dynamicId,1);
		}
		if(status==1){//现在取消点赞
			userDynamicDao.updateCommentLike(commentId,userId,0);
			userDynamicDao.updateCommentLikenum(commentId,-1);
			userDynamicRedisDao.commentLike(commentId,dynamicId,-1);
		}
	}


}










