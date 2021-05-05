package com.vrktech.springboot.gitactivities.exception;

public class GitCustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2481405170471753282L;

	Object errorCode = null;
	Object[] args = null;

	public GitCustomException(Object errorCode) {
		this.errorCode = errorCode;
	}

	public GitCustomException(Object errorCode, Object[] args) {
		this.errorCode = errorCode;
		this.args = args;
	}

	public GitCustomException(Object errorCode, Throwable throwable) {
		super(throwable);
		this.errorCode = errorCode;
	}

	public GitCustomException(Object errorCode, Object[] args, Throwable throwable) {
		super(throwable);
		this.errorCode = errorCode;
		this.args = args;
	}

	public Object getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Object errorCode) {
		this.errorCode = errorCode;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}