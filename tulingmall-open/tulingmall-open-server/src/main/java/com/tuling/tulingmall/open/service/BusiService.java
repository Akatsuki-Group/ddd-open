package com.tuling.tulingmall.open.service;

import com.tuling.tulingmall.module.mobilearea.api.MobileAreaFeignApi;
import com.tuling.tulingmall.module.mobiletag.api.MobileTagFeignApi;
import com.tuling.tulingmall.module.mobilearea.MobileAreaCallbackV2;
import com.tuling.tulingmall.module.mobilearea.PhoneInfo;
import com.tuling.tulingmall.module.mobiletag.MobileTagCallbackV2;
import com.tuling.tulingmall.open.entity.RequestMsg;
import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 具体处理业务逻辑
 */
@Service
public class BusiService {

	@Resource
	private TaskExecutor taskExecutor;
	@Resource
	private HisRequestService hisRequestService;
	
	@Resource
	private MobileTagFeignApi mobileTagFeignApi;
	
	@Resource
	private MobileAreaFeignApi mobileAreaFeignApi;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public void doProcess(String sysId,String transId, String serviceCode, Map<String, Object> requestMsg) {
		logger.info("start doProcess:sysId => "+sysId+";serviceCode => "+serviceCode+";requestMsg => "+requestMsg);
		// =================================手机号码标注业务==========================================
		if (RequestMsg.MOBILE_MARK_SERVICE.equals(serviceCode)) {
			logger.info("process entry => 进入手机号码标注接口对接");
			 // 转换body为业务对象
			PhoneInfo requestInfo = new PhoneInfo();
			 requestInfo.setTransId(transId);
			 requestInfo.setMobile(requestMsg.get("mobile").toString());
			 // 启动数据回调线程
			 taskExecutor.execute(new MobileTagCallbackV2(sysId,requestInfo,hisRequestService,mobileTagFeignApi));
			 // 返回正确接收请求的响应信息
			 logger.info("process info : 手机号码标注异步推送线程正常启动");
			// =================================手机号码归属省==========================================	 
		}else if(RequestMsg.MOBILE_AREA_SERVICE.equals(serviceCode)) {
			logger.info("process entry => 进入手机号码标注接口对接");
			// 转换body为业务对象
			PhoneInfo requestInfo = new PhoneInfo();
			requestInfo.setTransId(transId);
			requestInfo.setMobile(requestMsg.get("mobile").toString());
			// 启动数据回调线程
			taskExecutor.execute(new MobileAreaCallbackV2(sysId, requestInfo, hisRequestService, mobileAreaFeignApi));
			// 返回正确接收请求的响应信息
			logger.info("process info : 手机号码标注异步推送线程正常启动");
//		}else if("UtlmallMS".equals(serviceCode)){

		}else {
			logger.info("doProcess : entry => 没有匹配的接口，请确认serviceCode是否正确。");
		}
	}
}
