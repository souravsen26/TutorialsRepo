package com.infosys.infytel.friend.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;

public class LoggingAspect {
	
	Log logger = LogFactory.getLog(this.getClass()) ;

	@AfterThrowing(pointcut = "execution(* com.infosys.infytel.calldetails.*.*.*(...))", throwing = "exception")
	public void afterThrowing(Exception exception) {
		logger.error(exception.getMessage()) ;
	}
	
}
