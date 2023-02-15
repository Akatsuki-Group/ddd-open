package com.tuling.tulingmall.open.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuling.tulingmall.open.entity.ClientInfo;
import com.tuling.tulingmall.open.entity.RequestMsgHeaderV2;
import com.tuling.tulingmall.open.entity.RequestMsgV2;
import com.tuling.tulingmall.open.entity.ResponseMsgV2;
import com.tuling.tulingmall.open.util.AESUtil;
import com.tuling.tulingmall.open.util.ConfigUtils;
import com.tuling.tulingmall.open.util.RsaUtils;
import org.springframework.stereotype.Service;

@Service
public class DecrypService {
	/**
	 * 报文解密方式：对请求报文的body部分进行AES对称加密。AES的密钥用RSA非对称加密进行加密。
	 * 解密步骤：
	 * 1、通过SysId找到对应的系统的privateKey
	 * 2、利用改系统的privateKey,对header中的key字段(key字段应该是客户端利用对应的PublicKey加密后的加密串)，利用RSA解密，获取对body加密的AESKey
	 * 3、利用AESKey对报文Body进行解密
	 */
	public JSONObject decrypData(JSONObject requestInfo, JSONObject res) {
		JSONObject jRes = null;
		ResponseMsgV2 respMessage = new ResponseMsgV2();
		
		RequestMsgV2 requestBean = JSONObject.parseObject(requestInfo.toJSONString(), RequestMsgV2.class);
		if(null == requestBean){
			respMessage.setCode(ResponseMsgV2.CODE_MISSING_PARAM);
			respMessage.setMessage(ResponseMsgV2.MESSAGE_MISSING_PARAM);
			res.put("message", respMessage);
		}else{
			String sBody = "";
			try{
				RequestMsgHeaderV2 reqHeader = requestBean.getHeader();
				String encrypedBody = requestBean.getBody();
				//获取系统的相关配置信息
				ClientInfo clientInfo = ConfigUtils.getClientInfo(reqHeader.getSysId());
				//解密
				String aesKey = RsaUtils.RSADecodeByPrivateKey(clientInfo.getPrivatekey(), reqHeader.getKey());
				sBody = AESUtil.decryptByAES(encrypedBody, aesKey);
			}catch(Exception e){
				respMessage.setCode(ResponseMsgV2.CODE_DECRYPED_ERROR);
				respMessage.setMessage(ResponseMsgV2.MESSAGE_DECRYPED_ERROR);
				res.put("message", respMessage);
			}
			sBody = sBody.replace("\"","").replace("\\","\"");
			requestInfo.put("body",JSON.parseObject(sBody));
			jRes = requestInfo;
		}
		
		return jRes;
	}
}
