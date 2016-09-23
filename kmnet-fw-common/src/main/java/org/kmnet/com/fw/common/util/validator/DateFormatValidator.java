package org.kmnet.com.fw.common.util.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

	private String checkType = null;
	
	@Override
	public void initialize(DateFormat constraintAnnotation) {

		checkType = constraintAnnotation.checkType();
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null||"".equals(value)) {
			return true;
		}
		if (checkType == null||"".equals(value)) {
			return false;
		}
    	try {
    		 SimpleDateFormat sf = new SimpleDateFormat(checkType);
             sf.setLenient(false);
             sf.parse(value);
    	} catch(ParseException e) {
    		return false;
    	}
    	return true;
	}

}
