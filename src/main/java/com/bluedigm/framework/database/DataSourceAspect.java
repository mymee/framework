/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.database;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * The Class DataSourceAspect.
 */
@Aspect
@Component
@Order(value = 1)
public class DataSourceAspect{

	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Do service profiling.
	 * 
	 * @param joinPoint the join point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("execution(* com.bluedigm..*Service.*(..))")
	public Object doServiceProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("@Dao Service");

		// Annotation을 읽어 들이기 위해 현재의 method를 읽어 들인다.
		final String methodName = joinPoint.getSignature().getName();
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
		}
		// Annotation을 가져온다.
		DataSource dataSource = (DataSource) method.getAnnotation(DataSource.class);
		if (dataSource != null) {
			// Method에 해당 dataSource관련 설정이 있을 경우 해당 dataSource의 value를 읽어 들인다.
			ContextHolder.setDataSourceType(dataSource.value());
		} else {
			ContextHolder.setDataSourceType(DataSourceType.DEFAULT);
		}
		log.debug("DataSource ===> " + ContextHolder.getDataSourceType());
		
		Object returnValue = joinPoint.proceed();
		ContextHolder.clearDataSourceType();
		log.debug("@Dao Service");
		return returnValue;
	}

}
