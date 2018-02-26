package com.biz.std.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 11785
 */
public class DateConveter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		try {
			if(source != null && source.trim().length() != 0) {
			DateFormat dF = new SimpleDateFormat("yyyy-MM-dd");
			return dF.parse(source);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
