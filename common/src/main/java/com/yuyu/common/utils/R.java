package com.yuyu.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;

public class R extends HashMap<String, Object> {

	private static final long serialVersionUID = -5192933554896614373L;
	
	private R() { }
	
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "fail");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok() {
		return ok("success");
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("code", HttpStatus.SC_OK);
		r.put("msg", msg);
		return r;
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Integer getCode() {
		return (Integer) this.get("code");
	}

	public String getMsg() {
		return (String) this.get("msg");
	}
}
