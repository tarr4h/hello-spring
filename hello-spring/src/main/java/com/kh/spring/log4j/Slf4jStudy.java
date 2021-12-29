package com.kh.spring.log4j;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slf4jStudy {
	
//	private static Logger log = LoggerFactory.getLogger(Slf4jStudy.class);

	public static void main(String[] args) {
//		log.fatal("fatal"); Slf4j 에는 fatal level이 없음
		log.error("error");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");
	}

}
