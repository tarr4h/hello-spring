package com.kh.spring.log4j;

import org.apache.log4j.Logger;

public class Log4jStudy {
	
	private static Logger log = Logger.getLogger(Log4jStudy.class);

	public static void main(String[] args) {
		log.fatal("fatal");
		log.error("error");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");

	}

}
