package com.vrktech.springboot.gitactivities.exception;

import java.util.Locale;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessageGenerator {
	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(ErrorMessageGenerator.class);

	@Autowired
	MessageSource messageSource;

	public ErrorMessage generateError(GitCustomException ex) {

		ErrorMessage error = new ErrorMessage();
		try {
			error.setCorrelationId(ThreadContext.get(" as of now empty correID"));
			error.setErrorCode(ex.getErrorCode().toString());
			error.setShortDescription(messageSource.getMessage(ex.getErrorCode().toString() + ".SHORT",
					ex.getArgs(), Locale.US));
			error.setDetailedDescription(messageSource
					.getMessage(ex.getErrorCode().toString() + ".DETAIL", ex.getArgs(), Locale.US));
			error.setStackTrace(ex.getMessage());

		} catch (Exception e) {
			logger.error("Unaable to generate error {} : " + ex.errorCode, e);
		}

		return error;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}