package com.vrktech.springboot.gitactivities.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	GIT_001, GIT_002, GIT_003, GIT_004, GIT_005, GIT_040, GIT_050, GIT_090, GIT_400, GIT_404, GIT_405, GIT_451, GIT_450;

	public static HttpStatus httpStatusCode(ErrorCode errorCode) {
		HttpStatus statusCode;
		switch (errorCode) {
			case GIT_001:
			case GIT_002:
			case GIT_003:
			case GIT_004:
			case GIT_005:
			case GIT_040:
			case GIT_050:
			case GIT_090:
			case GIT_405:
			case GIT_451:

			case GIT_400:
				statusCode = HttpStatus.BAD_REQUEST;
				break;
			case GIT_404:
				statusCode = HttpStatus.NOT_FOUND;
				break;
			case GIT_450:
				statusCode = HttpStatus.FORBIDDEN;
				break;
			default:
				statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return statusCode;
	}
}