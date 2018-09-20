package com.skywing.utils.exception;

public class BaseException  extends RuntimeException{
	
	     public BaseException(Throwable throwable) {
		    super(throwable);
		  }

		  public BaseException(String msg, Throwable throwable) {
		    super(msg, throwable);
		  }

		  public BaseException(String msg) {
		    super(msg);
		  }

}
