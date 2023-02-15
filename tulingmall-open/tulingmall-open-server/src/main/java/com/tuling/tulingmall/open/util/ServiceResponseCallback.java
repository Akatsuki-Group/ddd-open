package com.tuling.tulingmall.open.util;

import com.tuling.tulingmall.open.entity.ServiceResponseEntity;

/**
 * 服务发送回调
 */
public interface ServiceResponseCallback {
	/**
	 * 处理响应结果
	 * 
	 * @param serviceResponseEntity
	 */
	void processResponse(ServiceResponseEntity serviceResponseEntity);
}
