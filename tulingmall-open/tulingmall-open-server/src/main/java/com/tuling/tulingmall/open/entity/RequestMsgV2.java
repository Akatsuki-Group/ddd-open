package com.tuling.tulingmall.open.entity;

/**
 * 请求信息第二版，对应crawlerController中v2版本接口。 增加接口鉴权功能。
 * @author
 */
public class RequestMsgV2 {

	private RequestMsgHeaderV2 header;
	private String body;
	private ResponseMsgV2 message;
	public RequestMsgHeaderV2 getHeader() {
		return header;
	}
	public void setHeader(RequestMsgHeaderV2 header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public ResponseMsgV2 getMessage() {
		return message;
	}
	public void setMessage(ResponseMsgV2 message) {
		this.message = message;
	}
}
