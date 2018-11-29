package com.jian.portal.reg.controller;

import com.jian.core.model.bean.Comment;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.model.bean.Reply;
import com.jian.core.model.util.JsonResult;
import com.jian.core.model.util.PicUtil;
import com.jian.core.model.util.UploadFile;
import com.jian.core.server.service.UserDynamicService;
import com.jian.portal.reg.token.Token;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户动态 评论 回复 点赞 controller
 * @author shen
 *
 */
@RequestMapping(value="/userDynamic")
@RestController
public class UserDynamicController {
	
	@Resource
	private UserDynamicService userDynamicService;
	
	/**
	 * 上传动态
	 * @param file  动态图片
	 * @param content  动态内容
	 * @return
	 */
	@Token
	@RequestMapping(value="/publish",method= RequestMethod.POST)
	public JsonResult publish(HttpServletRequest request, MultipartFile file[], @RequestParam String content) {
		JsonResult result = new JsonResult();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		if(userId == null || userId<=0) {
			result.setCode(201);
			result.setMessage("用户未登录");
			return result;
		}
		if(file.length==0 || file==null) {
			result.setCode(202);
			result.setMessage("无图片上传");
			return result;
		}
		
		if(content==null || content.trim().length()==0) {
			result.setCode(203);
			result.setMessage("动态内容为空");
			return result;
		}
		
		int dynamic = userDynamicService.publish(userId, content);
		if(dynamic<=0) {
			result.setCode(204);
			result.setMessage("发布动态失败");
			return result;
		}
		String imgPath = "F:/good/eclipse/jiankeji/ChongPet/src/main/resources/static/img/userDynamic/";
		for(MultipartFile f:file) {
			try {
				String imgName =System.currentTimeMillis()+ f.getOriginalFilename();
				String path = imgPath+imgName;
				String paths = "http://192.168.1.24:8080/img/userDynamic/"+imgName;
				UploadFile.uploadFileUtil(f.getBytes(), imgPath, imgName);
				PicUtil.compressPhoto(path);
				userDynamicService.publishimg(paths, dynamic);
			} catch (Exception e) {
				result.setCode(205);
				result.setMessage("动态图片上传失败");
				return result;
			}
		}
		result.setCode(200);
		result.setMessage("发布动态成功");
		return result;
	}
	
	/**
	 * 点赞 或 取消赞
	 * @param dynamic 动态id
	 * @param userId  点赞或取消赞用户
	 * @return
	 */
	@Token
	@RequestMapping(value="/likeOrCancel",method= RequestMethod.POST)
	public JsonResult likeOrCancel(Integer dynamic,Integer userId) {
		JsonResult result  = new JsonResult();
		if(dynamic==null || dynamic<=0) {
			result.setCode(201);
			result.setMessage("动态id为空或无效");
			return result;
		}
		if(userId==null || userId<=0) {
			result.setCode(202);
			result.setMessage("点赞或取消赞的用户为空或不存在 ");
			return result;
		}
		int likenum = userDynamicService.like(dynamic, userId);
		result.setCode(200);
		result.setMessage("点赞或取消赞成功");
		result.setData(likenum);
		return result;
	}
	
	/**
	 * 显示动态 
	 * @param pageNum 页数
	 * @param pageSize 一页个数
	 * @return
	 */
	@Token
	@RequestMapping(value = "/showDynamic",method= RequestMethod.POST)
	public JsonResult showDynamic(Integer pageNum,Integer pageSize) {
		JsonResult result  = new JsonResult();
		if(pageNum<=0 || pageNum==null) {
			result.setCode(201);
			result.setMessage("页数无效");
			return result;
		}
		if(pageSize<=0 || pageSize == null) {
			result.setCode(202);
			result.setMessage("一页显示多少无效");
			return result;
		}
		List<Dynamic> list = userDynamicService.showDynamic(pageNum, pageSize);
		result.setCode(200);
		result.setMessage("查询成功");
		result.setData(list);
		return result;
	}
	
	/**
	 * 评论
	 * @param comment
	 * @return
	 */
	@Token
	@RequestMapping(value = "/comment",method = RequestMethod.POST)
	public JsonResult comment(HttpServletRequest request, Comment comment) {
		JsonResult result  = new JsonResult();
		Integer uid = (Integer) request.getSession().getAttribute("userId");
		if(uid == null || uid<=0) {
			result.setCode(201);
			result.setMessage("用户未登录");
			return result;
		}
		comment.setFromUserId(uid);
		try {
			if(comment.getDynamicId()<=0)throw new  Exception("");
		} catch (Exception e) {
			result.setCode(202);
			result.setMessage("评论的动态无效");
			return result;
		}
		try {
			if(comment.getToUserId()<=0)throw new  Exception("");
		} catch (Exception e) {
			result.setCode(203);
			result.setMessage("动态作者为空");
			return result;
		}
		try {
			if(comment.getCommentContent().trim().length()==0) throw new  Exception("");
		} catch (Exception e) {
			result.setCode(204);
			result.setMessage("评论内容为空");
			return result;
		}

		userDynamicService.comment(comment);
		result.setCode(200);
		result.setMessage("评论成功");
		return result;
	}
	
	/**
	 * 回复
	 * @param reply
	 * @return
	 */
	@Token
	@RequestMapping(value = "/reply",method = RequestMethod.POST)
	public JsonResult reply(HttpServletRequest request, Reply reply) {
		JsonResult result  = new JsonResult();
		Integer uid = (Integer) request.getSession().getAttribute("userId");
		if(uid == null || uid<=0) {
			result.setCode(201);
			result.setMessage("用户未登录");
			return result;
		}
		reply.setFromUserId(uid);
		try {
			if (reply.getCommentId()<=0) throw new  Exception("");
		} catch (Exception e) {
			result.setCode(202);
			result.setMessage("评论id无效");
			return result;
		}
		try {
			if (reply.getToUserId()<=0) throw new  Exception("");
		} catch (Exception e) {
			result.setCode(203);
			result.setMessage("被回复者无效");
			return result;
		}
		
		try {
			if (reply.getReplyContent().trim().length()==0) throw new  Exception("");
		} catch (Exception e) {
			result.setCode(204);
			result.setMessage("回复内容为空");
			return result;
		}
		
		userDynamicService.reply(reply);
		result.setCode(200);
		result.setMessage("回复成功");
		return result;
	}
	
	/**
	 * 删除回复
	 * @param request
	 * @param replyId
	 * @return
	 */
	@Token
	@RequestMapping(value = "/deleteReply",method = RequestMethod.POST)
	public JsonResult deleteReply(HttpServletRequest request, Integer replyId) {
		JsonResult result  = new JsonResult();
		Integer userId =  (Integer) request.getSession().getAttribute("userId");
		if(userId == null || userId<=0) {
			result.setCode(201);
			result.setMessage("用户未登录");
			return result;
		}
		if(replyId<=0 || replyId==null) {
			result.setCode(202);
			result.setMessage("需删除的回复为空");
			return result;
		}
		int status = userDynamicService.deleteReply(replyId, userId);
		if(status==203) {
			result.setCode(203);
			result.setMessage("您没有权限删除");
			return result;
		}
		result.setCode(200);
		result.setMessage("回复删除成功");
		return result;
	}

	/**
	 * 删除评论
	 * @param request
	 * @param commentId
	 * @return
	 */
	@Token
	@RequestMapping(value = "/deleteComment",method = RequestMethod.POST)
	public JsonResult deleteComment(HttpServletRequest request, Integer commentId) {
		JsonResult result  = new JsonResult();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		if(userId == null || userId<=0) {
			result.setCode(201);
			result.setMessage("用户未登录");
			return result;
		}
		if(commentId==null || commentId<=0) {
			result.setCode(202);
			result.setMessage("需要删除的评论无效");
			return result;
		}
		
		int status = userDynamicService.deleteComment(commentId, userId);
		if(status==203) {
			result.setCode(203);
			result.setMessage("您没有权限删除");
			return result;
		}
		result.setCode(200);
		result.setMessage("评论删除成功");
		return result;
	}
	
	/**
	 * 删除动态
	 * @param request
	 * @return
	 */
	@Token
	@RequestMapping(value = "/deleteDynamic",method = RequestMethod.POST)
	public JsonResult deleteDynamic(HttpServletRequest request, Integer dynamicId) {
		JsonResult result = new JsonResult();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		if(userId == null || userId<=0) {
			result.setCode(201);
			result.setMessage("用户未登录");
			return result;
		}
		if(dynamicId == null || dynamicId<=0) {
			result.setCode(202);
			result.setMessage("要删除的动态无效");
			return result;
		}
		
		int status = userDynamicService.deleteDynamic(dynamicId, userId);
		if(status==203) {
			result.setCode(203);
			result.setMessage("您没有权限删除");
			return result;
		}
		result.setCode(200);
		result.setMessage("动态删除成功");
		return result;
	}
}






