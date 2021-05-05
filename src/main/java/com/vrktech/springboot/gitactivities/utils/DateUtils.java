package com.vrktech.springboot.gitactivities.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class DateUtils {

	public Date getWeekDateFromMilliSec(long millisec) throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateInString = simpleDateFormat.format(new Date(millisec * 1000));
		return LocalDate.parse(dateInString).toDate();

	}

	public Date getNextDayForTheGivenDate(Date givenDay) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(givenDay);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

}
