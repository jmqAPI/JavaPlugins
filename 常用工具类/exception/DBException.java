package com.skywing.utils.exception;

import org.apache.log4j.Logger;

public class DBException extends RuntimeException {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DBException.class);
	
	public DBException(Throwable throwable) {
		    super(throwable);
		    logger.info("throwable {" + throwable + "}");
		  }

		  public DBException(String msg, Throwable root) {
		    super(msg, root);
     	    logger.info("msg {" + msg + " } Throwable {" + root + "}");
		  }

		  public DBException(String msg) {
		    super(msg);
		    logger.info("msg {" + msg + "}");
		  }

}
