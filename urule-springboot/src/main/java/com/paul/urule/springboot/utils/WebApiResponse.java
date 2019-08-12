package com.paul.urule.springboot.utils;

import lombok.Data;

/**
 * 返回数据
 *
 * @author jack.shuang
 */
@Data
public class WebApiResponse {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public WebApiResponse code(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public WebApiResponse msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public WebApiResponse data(Object data) {
        this.data = data;
        return this;
    }

	/**
	 * 增加一个有自定义消息的会话
	 * @param data
	 * @param msg
	 * @return
	 */
	public static WebApiResponse successDataAndMsg(Object data,String msg) {

		return new WebApiResponse().code(ResponseStatus.HTTP_OK).msg(msg).data(data);
	}
    public static WebApiResponse success(Object data) {

        return new WebApiResponse().code(ResponseStatus.HTTP_OK).msg("成功!").data(data);
    }

    public static WebApiResponse failure(String msg) {

        return failure(ResponseStatus.HTTP_INTERNAL_ERROR, msg);
    }

    public static WebApiResponse failure(int code, String msg) {

        return new WebApiResponse().code(code).msg(msg);
    }

}
