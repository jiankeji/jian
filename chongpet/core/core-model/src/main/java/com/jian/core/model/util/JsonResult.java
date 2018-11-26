package com.jian.core.model.util;

import java.util.Map;

/**
 * 返回统一
 * @author shen
 *
 */
public class JsonResult {

	private final static int SUCCESS =200;
	private final static int ERROR =600;
	
	
	private int code;
	
	private String message;
	
	private Object data;

	
	public JsonResult(Map<String, Object> map) {
		this.code=SUCCESS;
		this.message="";
		this.data=map;
	}
	
	public JsonResult() {
		this.code=SUCCESS;
		this.message="";
		this.data="";
	}
	
	public JsonResult(Throwable t) {
		this.code=ERROR;
		this.message=t.getMessage();
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}



	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
	
	
}




