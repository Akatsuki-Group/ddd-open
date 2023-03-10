package com.tlmall.open.domain.genservice.entity;

/**
 * @author ：楼兰
 **/

public class EncryptedRequestMsg {

    private EncryptedRequestMsgHeader header;
    private String body;

    public EncryptedRequestMsgHeader getHeader() {
        return header;
    }

    public void setHeader(EncryptedRequestMsgHeader header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EncryptedRequestMsg{" +
                "header=" + header +
                ", body='" + body + '\'' +
                '}';
    }
}
