package com.jian.portal.reg.controller;

import com.jian.core.model.bean.Comment;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.model.bean.Img;
import com.jian.core.model.bean.Reply;

import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.PicUtil;
import com.jian.core.model.util.PropertiesUtil;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.model.util.UploadFile;
import com.jian.core.server.service.UserDynamicService;
import com.jian.portal.reg.token.Token;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.jian.core.model.ResC.*;
import static com.jian.core.model.bean.inter.ImgUrls.DYNAMIC;
import static com.jian.core.model.bean.inter.ImgUrls.IMGSRC;

/**
 * 用户动态 评论 回复 点赞 controller
 * @author shen
 *
 */
@SuppressWarnings("ALL")
@RequestMapping(value="api1/userDynamic")
@RestController
@Api(value ="api1/login",description = "用户动态 评论 回复")
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
    @PostMapping(value="/publish",headers = "content-type=multipart/form-data",consumes="multipart/*")
    @ApiOperation(value="上传动态", notes="上传动态", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "content",value = "标题 内容",dataType = "String",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<Integer> publish(HttpServletRequest request,
                                   @ApiParam (value = "文件",required =true)@RequestParam(value = "file",required = true) MultipartFile [] file, @RequestParam String content) {
        ResultVo<Integer> result = new ResultVo(-1);
        String token = request.getHeader("token");
        if(file.length==0 || file==null) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }

        if(content==null || content.trim().length()==0) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }

        Dynamic dynamic = userDynamicService.publish(token, content);
        List<Img> imgList = new ArrayList<>();
        if(dynamic.getDynamicId()<=0) {
            return ResultUtil.setResultVoDesc(result,API_EXCEPTION);
        }

        String imgPath = PropertiesUtil.getProperty(IMGSRC)+PropertiesUtil.getProperty(DYNAMIC);

        for(MultipartFile f:file) {
            try {
                String imgName =System.currentTimeMillis()+ f.getOriginalFilename();
                String path = imgPath+imgName;
                String paths = PropertiesUtil.getProperty(DYNAMIC)+imgName;
                UploadFile.uploadFileUtil(f.getBytes(), imgPath, imgName);
                PicUtil.compressPhoto(path);
                Img img = userDynamicService.publishimg(paths, dynamic.getDynamicId());
                imgList.add(img);
            } catch (Exception e) {
                return ResultUtil.setResultVoDesc(result,API_EXCEPTION);
            }
        }
        dynamic.setImgList(imgList);
        userDynamicService.publistRedis(dynamic);
        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }

    /**
     * 点赞 或 取消赞
     * @param dynamicId 动态id
     * @param userId  点赞或取消赞用户
     * @return
     */
    @Token
    @PostMapping(value="/likeOrCancel",produces = "application/json; charset=UTF-8")
    @ApiOperation(value="点赞 取消赞", notes="上传动态", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "dynamicId",value = "动态id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query",name = "userId",value = "点赞的人id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<Integer> likeOrCancel(@RequestParam Integer dynamicId,@RequestParam Integer userId) {
        ResultVo<Integer> result = new ResultVo<>(-1);
        if(dynamicId==null || dynamicId<=0) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }
        if(userId==null || userId<=0) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }
        int likenum = userDynamicService.like(dynamicId, userId);
        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }

    /**
     * 显示动态
     * @param pageNum 页数
     * @param pageSize 一页个数
     * @return
     */
    @Token
    @PostMapping(value="/showDynamic",produces = "application/json; charset=UTF-8")
    @ApiOperation(value="显示动态", notes="显示动态", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageSize",value = "一页个数",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query",name = "pageNum",value = "第几页",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true),
            @ApiImplicitParam(paramType = "query",name = "userId",value = "用户id 选填 配合status",dataType = "int",required = false),
            @ApiImplicitParam(paramType = "query",name = "status",value = "查找状态 0查全都动态 1 查userId的 2查userId关注的",dataType = "int",required = true)
    })
    public ResultVo<List<Dynamic>> showDynamic(HttpServletRequest request,@RequestParam  Integer pageNum,
                               @RequestParam Integer pageSize, @RequestParam Integer status,@RequestParam(required = false) Integer userId) {
        List<Dynamic> list = new ArrayList<>();
        ResultVo<List<Dynamic>> result = new ResultVo<>(list);
        String token = request.getHeader("token");
        if(pageNum<=0 || pageNum==null) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }
        if(pageSize<=0 || pageSize == null) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }
        if(status == null||status<0||status>2) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }

        if(userId == null || userId <=0){
            userId=-1;
        }

        list = userDynamicService.showDynamic(pageNum, pageSize,userId,status);
        result.setObj(list);
        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }

    /**
     * 评论
     * @param
     * @return
     */
    @Token
    @PostMapping(value="/comment",produces = "application/json; charset=UTF-8")
    @ApiOperation(value="评论", notes="评论", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "toUserId",value = "动态作者id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query",name = "dynamicId",value = "动态id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query",name = "commentContent",value = "评论内容",dataType = "String",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<Integer> comment(HttpServletRequest request,@RequestParam  Integer toUserId,@RequestParam Integer dynamicId,
                                     @RequestParam String commentContent) {
        ResultVo<Integer>  result  = new ResultVo<>(-1);
        Comment comment = new Comment();
        comment.setCommentContent(commentContent);
        comment.setDynamicId(dynamicId);
        comment.setToUserId(toUserId);

        String token = request.getHeader("token");

        if(toUserId==null||toUserId<=0)return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        if(dynamicId==null||dynamicId<=0)return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        if(commentContent==null)return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);

        userDynamicService.comment(comment,token);

        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }

    /**
     * 回复
     * @param
     * @return
     */
    @Token
    @PostMapping(value="/reply",produces = "application/json; charset=UTF-8")
    @ApiOperation(value="回复", notes="回复", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "toUserId",value = "被回复者id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query",name = "commentId",value = "评论id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query",name = "replyContent",value = "回复内容",dataType = "String",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<Integer> reply(HttpServletRequest request,@RequestParam  Integer toUserId,@RequestParam Integer commentId,@RequestParam String replyContent) {
        ResultVo<Integer> result  = new ResultVo<>(-1);

        Reply reply = new Reply();
        reply.setCommentId(commentId);
        reply.setToUserId(toUserId);
        reply.setReplyContent(replyContent);

        String token = request.getHeader("token");

        if(toUserId==null||toUserId<=0)return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        if(commentId==null||commentId<=0)return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        if(replyContent==null)return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);

        userDynamicService.reply(reply,token);

        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }

    /**
     * 删除回复
     * @param request
     * @param replyId
     * @return
     */
    @Token
    @PostMapping(value = "/deleteReply",produces = "application/json; charset=utf-8")
    @ApiOperation(value="删除回复", notes="删除回复", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入"),
            @ApiResponse(code=API_USER_NOT_POWER,message="您没有权限")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "commentId",value = "评论id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query",name = "replyId",value = "回复id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<Integer> deleteReply(HttpServletRequest request,@RequestParam  Integer replyId,@RequestParam  Integer commentId) {
        ResultVo<Integer>  result  = new ResultVo<>(-1);
        String token = request.getHeader("token");
        if(replyId<=0 || replyId==null) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }
        if(commentId<=0 || commentId==null) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }
        int status = userDynamicService.deleteReply(replyId,commentId, token);
        if(status==203) {
            return ResultUtil.setResultVoDesc(result,API_USER_NOT_POWER);
        }

        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }

    /**
     * 删除评论
     * @param request
     * @param commentId
     * @return
     */
    @Token
    @PostMapping(value = "/deleteComment",produces = "application/json; charset=utf-8")
    @ApiOperation(value="删除评论", notes="删除评论", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入"),
            @ApiResponse(code=API_USER_NOT_POWER,message="您没有权限")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "commentId",value = "评论id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query",name = "dynamicId",value = "动态id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<Integer> deleteComment(HttpServletRequest request,@RequestParam  Integer commentId,@RequestParam  Integer dynamicId) {
        ResultVo<Integer> result  = new ResultVo<>(-1);
        String token = request.getHeader("token");
        if(commentId==null || commentId<=0) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }

        int status = userDynamicService.deleteComment(commentId, dynamicId,token);
        if(status==203) {
            return ResultUtil.setResultVoDesc(result,API_USER_NOT_POWER);
        }

        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }

    /**
     * 删除动态
     * @param request
     * @return
     */
    @Token
    @PostMapping(value = "/deleteDynamic",produces = "application/json; charset=utf-8")
    @ApiOperation(value="删除动态", notes="删除动态", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入"),
            @ApiResponse(code=API_USER_NOT_POWER,message="您没有权限")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "dynamicId",value = "动态id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<Integer> deleteDynamic(HttpServletRequest request,@RequestParam  Integer dynamicId) {
        ResultVo<Integer> result = new ResultVo<>(-1);
        String token = request.getHeader("token");
        if(dynamicId == null || dynamicId<=0) {
            return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }

        int status = userDynamicService.deleteDynamic(dynamicId, token);
        if(status==203) {
            return ResultUtil.setResultVoDesc(result,API_USER_NOT_POWER);
        }
        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }


    @Token
    @PostMapping(value = "/browse",produces = "application/json; charset=utf-8")
    @ApiOperation(value="浏览动态", notes="浏览动态", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "dynamicId",value = "动态id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<String> browse(@RequestParam Integer dynamicId){
        ResultVo<String> result = new ResultVo<>("");
        if(dynamicId==null||dynamicId<=0){
            return  ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
        }
        userDynamicService.browse(dynamicId);
        return ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }

    @Token
    @PostMapping(value = "/commentLike",produces = "application/json; charset=utf-8")
    @ApiOperation(value="动态评论点赞 取赞", notes="动态评论点赞 取赞", response= ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "commentId",value = "评论id",dataType = "int",required = true),
            @ApiImplicitParam(paramType = "header",name = "token",value = "token",dataType = "String",required = true)
    })
    public ResultVo<String> commentLike (HttpServletRequest request,@RequestParam Integer commentId){
        ResultVo<String> result = new ResultVo<>("");
        String token = request.getHeader("token");
        userDynamicService.commentLike(commentId,token);
        return  ResultUtil.setResultVoDesc(result,API_SUCCESS);
    }
}
