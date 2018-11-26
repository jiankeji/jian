package com.jian.core.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.jian.core.model.bean.Comment;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.model.bean.Img;
import com.jian.core.model.bean.Reply;
import com.jian.core.model.util.UploadFile;
import com.jian.core.server.dao.UserDynamicDao;
import com.jian.core.server.service.UserDynamicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

	@Override
	public int publish(Integer userId, String content) {
		Dynamic dynamic = new Dynamic();
		dynamic.setContent(content);
		dynamic.setBrowse(0);
		dynamic.setCreateTime(System.currentTimeMillis());
		dynamic.setLikenum(0);
		dynamic.setUserId(userId);
		userDynamicDao.addDynamic(dynamic);
		return dynamic.getDynamicId();
	}

	@Override
	public int publishimg(String paths,Integer dynamicId) {
		userDynamicDao.addDynamicImg(paths, dynamicId);
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
			}
			if(likeStatus == 1) {
				userDynamicDao.updateLikeStatus(dynamicId, userId, 0);
				userDynamicDao.updateLikeNum(dynamicId, -1);
			}
		} catch (Exception e) {
			likeStatus = -1;
		}
		if(likeStatus == -1) {
			userDynamicDao.addUserLike(dynamicId, userId);
			userDynamicDao.updateLikeNum(dynamicId, 1);
		}
		int likenum = userDynamicDao.selectLikeNum(dynamicId);
		return likenum;
	}

	@Override
	public List<Dynamic> showDynamic(Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);
		List<Dynamic> dmcList= userDynamicDao.showDynamic();
		for(Dynamic dmc:dmcList) {
			List<Img> imgList = userDynamicDao.selectDMCIMGByDMC(dmc.getDynamicId());
			userDynamicDao.browse(dmc.getDynamicId());
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
	public int comment(Comment comment) {
		comment.setCommentTime(System.currentTimeMillis());
		userDynamicDao.comment(comment);
		return 0;
	}

	@Override
	public int reply(Reply reply) {
		reply.setReplyTime(System.currentTimeMillis());
		userDynamicDao.reply(reply);
		return 0;
	}

	@Override
	public int deleteReply(Integer replyId,Integer userId) {
		int status ;
		try {
			status= userDynamicDao.selectReplyById(replyId, userId);
			userDynamicDao.deleteReply(replyId);
		} catch (Exception e) {
			status = 203;
		}
		return status;
	}

	@Override
	public int deleteComment(Integer commentId, Integer userId) {
		int status ;
		try {
			status = userDynamicDao.selectCommentById(commentId, userId);
			List<Reply> replyList = userDynamicDao.selectReplyByCMT(commentId);
			for(Reply reply:replyList) {
				userDynamicDao.deleteReply(reply.getReplyId());
			}
			userDynamicDao.deleteComment(commentId);
		} catch (Exception e) {
			status = 203;
		}
		return status;
	}

	@Override
	public int deleteDynamic(Integer dynamicId, Integer userId) {
		int status;
		try {
			status = userDynamicDao.selectDynamicById(dynamicId, userId);
			List<Img> imgList = userDynamicDao.selectDMCIMGByDMC(dynamicId);
			for(Img img:imgList) {
				userDynamicDao.deleteImgById(img.getImgId());
				String path = img.getPhoto();
				String paths = "F:/good/eclipse/jiankeji/ChongPet/src/main/resources/static/img/userDynamic/"+path.substring(41);
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
		} catch (Exception e) {
			status=203;
		}
		return status;
	}
	
	
}










