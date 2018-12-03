package com.jian.core.server.redisDao;

import com.jian.core.model.bean.Comment;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.model.bean.Img;
import com.jian.core.model.bean.Reply;

import java.util.List;


public interface UserDynamicRedisDao {
    //发布动态
    void insertDynamic(Dynamic dynamic);
    void publishImg(Img img, Integer dynamicId, Integer userId);

    //点赞
    void dynamicLike(Integer dynamicId,Integer userId);
    //取消赞
    void cancelDynamicLike(Integer dynamicId,Integer userId);

    //显示动态
    List<Dynamic> showDynamic(Integer pageNum, Integer pageSize,Integer userId,Integer status);

    //评论
    void comment(Comment comment);
    //回复
    void reply(Reply reply);

    //删除回复
    void deleteReply(Integer replyId,Integer commentId);

    //删除评论
    void deleteComment(Integer commentId,Integer dynamicId);

    //删除动态
    void deleteDynamic(Integer dynamicId);
    //浏览动态
    void browse(Integer dynamicId);
    //评论点赞、取赞  status=1点赞 status=-1取消赞
    void commentLike(Integer commentId,Integer dynamicId,Integer status);
}
