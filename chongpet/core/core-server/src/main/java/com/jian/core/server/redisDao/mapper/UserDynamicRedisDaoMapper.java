package com.jian.core.server.redisDao.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jian.core.model.bean.*;
import com.jian.core.model.util.PropertiesUtil;
import com.jian.core.redis.util.RedisUtil1;
import com.jian.core.redis.util.RedisUtil2;
import com.jian.core.server.redisDao.UserDynamicRedisDao;
import com.jian.core.server.redisDao.UserRedisDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.jian.core.model.bean.inter.Constant.*;
import static com.jian.core.model.bean.inter.ImgUrls.PATH;

@Component
public class UserDynamicRedisDaoMapper implements UserDynamicRedisDao {


    //1号库
    @Resource
    private RedisUtil1 redisUtil1;
    @Resource
    private RedisUtil2 redisUtil2;
    @Resource
    private UserRedisDao userRedisDao;


    @Override
    public void insertDynamic(Dynamic dynamic) {

        //该用户的
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC+dynamic.getUserId(),dynamic.getDynamicId()+"", JSON.toJSONString(dynamic));
        redisUtil1.zAdd(REDIS_HOME_DYNAMIC_TIME+dynamic.getUserId() ,dynamic.getDynamicId()+"",dynamic.getCreateTime());

        //总的
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC,dynamic.getDynamicId()+"", JSON.toJSONString(dynamic));
        redisUtil1.zAdd(REDIS_HOME_DYNAMIC_TIME,dynamic.getDynamicId()+"",dynamic.getCreateTime());
    }

    @Override
    public void publishImg(Img img,Integer dynamicId,Integer userId) {
        String data = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC+userId,dynamicId+"");
        Dynamic dynamic = JSON.parseObject(data,Dynamic.class);
        List<Img> list = new ArrayList<>();
        list.add(img);
        dynamic.setImgList(list);
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC+userId,dynamicId+"",JSON.toJSONString(dynamic));

        String dataAll = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC,dynamicId+"");
        Dynamic dynamicAll = JSON.parseObject(dataAll,Dynamic.class);
        List<Img> lists = new ArrayList<>();
        lists.add(img);
        dynamicAll.setImgList(lists);
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC,dynamicId+"",JSON.toJSONString(dynamicAll));

    }

    @Override
    public void dynamicLike(Integer dynamicId, Integer userId) {
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC_LIKE+dynamicId,dynamicId+"",userId+"");

        String data = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC,dynamicId+"");
        Dynamic dynamic = JSON.parseObject(data,Dynamic.class);
        dynamic.setLikenum(dynamic.getDynamicId()+1);
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC,dynamicId+"",JSON.toJSONString(dynamic));
        String uid = dynamic.getUserId()+"";

        String datas = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC+uid,dynamicId+"");
        Dynamic dynamics= JSON.parseObject(datas,Dynamic.class);
        dynamics.setLikenum(dynamic.getDynamicId()+1);
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC+uid,dynamicId+"",JSON.toJSONString(dynamic));
    }

    @Override
    public void cancelDynamicLike(Integer dynamicId, Integer userId) {
        redisUtil1.hmDel(REDIS_HOME_DYNAMIC_LIKE+dynamicId,dynamicId+"");

        String data = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC,dynamicId+"");
        Dynamic dynamic = JSON.parseObject(data,Dynamic.class);
        dynamic.setLikenum(dynamic.getDynamicId()-1);
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC,dynamicId+"",JSON.toJSONString(dynamic));

        String uid = dynamic.getUserId()+"";

        String datas = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC+uid,dynamicId+"");
        Dynamic dynamics= JSON.parseObject(datas,Dynamic.class);
        dynamics.setLikenum(dynamic.getDynamicId()-1);
        redisUtil1.setHashValue(REDIS_HOME_DYNAMIC+uid,dynamicId+"",JSON.toJSONString(dynamic));
    }

    @Override
    public List<Dynamic> showDynamic(Integer pageNum, Integer pageSize,Integer userId,Integer status) {
        Integer before=(pageNum-1)*pageSize;
        Integer end = before+pageSize-1;

        Set<Object> set = new HashSet<>();

        if(status==0){
            set = redisUtil1.reverseRange(REDIS_HOME_DYNAMIC_TIME,before,end);
        }
        if(status==1){
            set = redisUtil1.reverseRange(REDIS_HOME_DYNAMIC_TIME+userId,before,end);
        }
        List<Dynamic> list = new ArrayList<>();
        if(status==2){
            if(redisUtil1.exists(REDIS_HOME_DYNAMIC_ATTENTION+userId)){
                String data = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_ATTENTION+userId);
                list = JSONArray.parseArray(data,Dynamic.class);
                for(Dynamic d:list){
                    User user = userRedisDao.gerUserRedis(d.getUserId());
                    d.setName(user.getName());
                    d.setPhoto(user.getPhoto());
                    String p = PropertiesUtil.getProperty(PATH);
                    for(Img i:d.getImgList()){
                        i.setPhoto(p+i.getPhoto());
                    }
                    if(redisUtil1.exists(REDIS_HOME_DYNAMIC_COMMENT+d.getDynamicId())) {
                        String dataComment = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_COMMENT + d.getDynamicId());
                        List<Comment> commentList = JSON.parseArray(dataComment, Comment.class);

                        for (Comment c : commentList) {
                           namephoto(c);
                            boolean b = redisUtil1.exists(REDIS_HOME_DYNAMIC_REPLY + c.getCommentId());
                            if(b){
                                String dateReply = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_REPLY + c.getCommentId());
                                List<Reply> replyList = JSON.parseArray(dateReply, Reply.class);
                                for(Reply r:replyList){
                                    namephoto(r);
                                }
                                c.setReplyList(replyList);
                            }
                        }
                        d.setCommentList(commentList);
                    }
                }
            }
            return  list;
        }

        for(Object o:set){
            if(o instanceof String){
                String dynamicId = (String)o;
                String data = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC,dynamicId);
                Dynamic dynamic = JSON.parseObject(data,Dynamic.class);

                User user =  userRedisDao.gerUserRedis(dynamic.getUserId());
                dynamic.setName(user.getName());
                dynamic.setPhoto(user.getPhoto());
                String p = PropertiesUtil.getProperty(PATH);
                for(Img i:dynamic.getImgList()){
                    i.setPhoto(p+i.getPhoto());
                }
                if(redisUtil1.exists(REDIS_HOME_DYNAMIC_COMMENT+dynamicId)){
                    String dataComment = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_COMMENT+dynamicId);
                    List<Comment> commentList = JSON.parseArray(dataComment,Comment.class);
                    for(Comment c:commentList){
                        namephoto(c);
                        if(redisUtil1.exists(REDIS_HOME_DYNAMIC_REPLY+c.getCommentId())){
                            String dateReply = (String)redisUtil1.get(REDIS_HOME_DYNAMIC_REPLY+c.getCommentId());
                            List<Reply> replyList = JSON.parseArray(dateReply, Reply.class);
                            for(Reply r:replyList){
                                namephoto(r);
                            }
                            c.setReplyList(replyList);
                        }
                    }
                    dynamic.setCommentList(commentList);
                }
                list.add(dynamic);
            }
        }
        return list;
    }

    @Override
    public void comment(Comment comment) {

        boolean b = redisUtil1.exists(REDIS_HOME_DYNAMIC_COMMENT+comment.getDynamicId());
        List<Comment> list;

        if(b){
           String data = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_COMMENT+comment.getDynamicId());
           list = JSON.parseArray(data,Comment.class);
        }else{
            list=new ArrayList<>();
        }
        list.add(comment);
        String data = JSON.toJSONString(list);
        redisUtil1.set(REDIS_HOME_DYNAMIC_COMMENT+comment.getDynamicId(),data);
    }

    @Override
    public void reply(Reply reply) {

        boolean b = redisUtil1.exists(REDIS_HOME_DYNAMIC_REPLY+reply.getCommentId());
        List<Reply> list;
        if(b){
            String data = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_REPLY+reply.getCommentId());
            list = JSONArray.parseArray(data,Reply.class);
        }else {
            list=new ArrayList<>();
        }

        list.add(reply);
        String data2 = JSON.toJSONString(list);
        redisUtil1.set(REDIS_HOME_DYNAMIC_REPLY+reply.getCommentId(),data2);
    }

    @Override
    public void deleteReply(Integer replyId, Integer commentId) {
        String data = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_REPLY+commentId);
        List<Reply> list = JSON.parseArray(data,Reply.class);
        for (Reply r:list){
            if(replyId==r.getReplyId()){
                list.remove(r);
                break;
            }
        }
        redisUtil1.set(REDIS_HOME_DYNAMIC_REPLY+commentId,JSON.toJSONString(list));
    }

    @Override
    public void deleteComment(Integer commentId, Integer dynamicId) {
        redisUtil1.remove(REDIS_HOME_DYNAMIC_REPLY+commentId);

        String data = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_COMMENT+dynamicId);
        List<Comment> list = JSON.parseArray(data,Comment.class);
        for (Comment c:list){
            if(c.getCommentId()==commentId){
                list.remove(c);
                break;
            }
        }
        redisUtil1.set(REDIS_HOME_DYNAMIC_COMMENT+dynamicId,JSON.toJSONString(list));
    }

    @Override
    public void deleteDynamic(Integer dynamicId) {
        if(!redisUtil1.hasHashKey(REDIS_HOME_DYNAMIC,dynamicId+"")){
            return;
        }
        String data = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC,dynamicId+"");
        Dynamic dynamic = JSON.parseObject(data,Dynamic.class);
        //删除该用户的动态
        redisUtil1.hmDel(REDIS_HOME_DYNAMIC+dynamic.getUserId(),dynamicId+"");
        redisUtil1.zRem(REDIS_HOME_DYNAMIC_TIME+dynamic.getUserId(),dynamicId+"");
        //删除总动态
        redisUtil1.hmDel(REDIS_HOME_DYNAMIC,dynamicId+"");
        redisUtil1.zRem(REDIS_HOME_DYNAMIC_TIME,dynamicId+"");
        //删除关注动态
        Set<Object> set = redisUtil2.getFansRange(dynamic.getUserId(),0,-1);
        for(Object o:set){
            if(o instanceof String){
                String userId = (String)o;
                String dataAttention = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_ATTENTION+userId);
                List<Dynamic> list = JSON.parseArray(dataAttention,Dynamic.class);
                for (Dynamic d:list){
                    if(d.getDynamicId()==dynamicId){
                        list.remove(d);
                    }
                }
                redisUtil1.set(REDIS_HOME_DYNAMIC_ATTENTION+userId,JSON.toJSONString(list));
            }
        }

        String dataComment = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_COMMENT+dynamicId);
        List<Comment> list = JSON.parseArray(data,Comment.class);
        //删除该动态的评论
        redisUtil1.remove(REDIS_HOME_DYNAMIC_COMMENT+dynamicId);
        for (Comment c:list){
            //该动态的删除回复
            redisUtil1.remove(REDIS_HOME_DYNAMIC_REPLY+c.getCommentId()+"");
        }

    }

    @Override
    public void browse(Integer dynamicId) {
        String data = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC,dynamicId+"");
        Dynamic dynamic = JSON.parseObject(data,Dynamic.class);
        dynamic.setBrowse(dynamic.getBrowse()+1);

        String dataAll = redisUtil1.getHashValue(REDIS_HOME_DYNAMIC+dynamic.getUserId(),dynamicId+"");
        Dynamic dynamicAll = JSON.parseObject(dataAll,Dynamic.class);
        dynamicAll.setBrowse(dynamicAll.getBrowse()+1);

        Set set = redisUtil2.getFansRange(dynamic.getUserId(),0,-1);

        for(Object o:set){
            if(o instanceof String){
                String uid = (String)o;
                String dataAttention = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_ATTENTION+uid);
                List<Dynamic> list = JSON.parseArray(dataAttention,Dynamic.class);
                for(Dynamic d:list){
                    if(d.getDynamicId()==dynamicId){
                        d.setBrowse(d.getBrowse()+1);
                    }
                    break;
                }
                redisUtil1.set(REDIS_HOME_DYNAMIC_ATTENTION+uid,JSON.toJSONString(list));
            }
        }
    }

    @Override
    public void commentLike(Integer commentId,Integer dynamicId, Integer status) {
        String data = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_COMMENT+dynamicId);
        List<Comment> list = JSON.parseArray(data,Comment.class);
        for(Comment c:list){
            if(commentId==c.getCommentId()){
                c.setLikeNum(c.getLikeNum()+status);
            }
            break;
        }
        redisUtil1.set(REDIS_HOME_DYNAMIC_COMMENT+dynamicId,JSON.toJSONString(list));
    }

    public void namephoto(Object o){

        if(o instanceof Comment) {
             Comment r= (Comment)o;
            User To = userRedisDao.gerUserRedis(r.getToUserId());
            r.setToUserName(To.getName());
            r.setToUserPhoto(To.getPhoto());

            User From = userRedisDao.gerUserRedis(r.getFromUserId());
            r.setFromUserName(From.getName());
            r.setFromUserPhoto(From.getPhoto());
        }
        if(o instanceof Reply) {
            Reply r= (Reply)o;
            User To = userRedisDao.gerUserRedis(r.getToUserId());
            r.setToUserName(To.getName());
            r.setToUserPhoto(To.getPhoto());

            User From = userRedisDao.gerUserRedis(r.getFromUserId());
            r.setFromUserName(From.getName());
            r.setFromUserPhoto(From.getPhoto());
        }


    }

}
