package com.basewin.kms.util;

/**
 * @author gdh
 *
 * @param <T>
 */
public class Ret<T> {

	private Boolean success;
	private T data;
	private String error;
	private String msg;

	private Ret() {
	}

	private static <T> Ret<T> create(Boolean success, T data, String error, String msg) {
		Ret<T> ret = new Ret<T>();
		ret.setData(data);
		ret.setSuccess(success);
		ret.setError(error);
		ret.setMsg(msg);
		return ret;
	}


	public static <T> Ret<T> success() {
		return success(null, null);
	}

	public static <T> Ret<T> success(T data) {
		return success(data, "操作成功");
	}
	public static <T> Ret<T> success(T data, String msg) {
		return create(true, data, null, msg);
	}

	public static <T> Ret<T> fail(T data, String msg) {
		return create(false, data, null, msg);
	}

	public static <T> Ret<T> fail(T data) {
		return fail(data, "操作失败");
	}
	
	public static <T> Ret<T> error(String error, String msg){
		return create(false, null, error, msg);
	}

	public static <T> Ret<T> fail() {
		return fail(null, null);
	}

	private void setSuccess(Boolean success) {
		this.success = success;
	}

	private void setData(T data) {
		this.data = data;
	}

	private void setError(String error) {
		this.error = error;
	}

	private void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public T getData() {
		return data;
	}

	public String getError() {
		return error;
	}

	public String getMsg() {
		return msg;
	}
}
