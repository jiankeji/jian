package com.jian.core.model.obj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 返回前端的结果信息代码和描述以及数据
 * @ClassName ResultVo
 * @Description VO(value object) 值对象,通常用于业务层之间的数据传递，和SERVER一样也是仅仅包含数据而已。但应是抽象出的业务对象,可以和表对应,也可以不,这根据业务的需要.个人觉得同DTO(数据传输对象),在web上传递。
 * @author lxh
 * @Date 2018年11月20日 下午15:30:19
 * @version 1.0.0
 */
@ApiModel(value="返回前端的结果信息代码和描述以及数据")
	public class ResultVo<T> implements Serializable {
		public ResultVo(T obj){
			this.obj=obj;
		}

		@ApiModelProperty(value="返回信息代码,1 成功;2 其他失败", dataType="int" ,required=true)
		private int code = 0;

		@ApiModelProperty(value="返回信息描述", dataType="String",required=true)
		private String desc = "";

	@ApiModelProperty(value="返回的结果对象,Integer,String,List...", dataType="Object",required=false)
	private T obj;
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the obj
	 */
	public T getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(T obj) {
		this.obj = obj;
	}
}
