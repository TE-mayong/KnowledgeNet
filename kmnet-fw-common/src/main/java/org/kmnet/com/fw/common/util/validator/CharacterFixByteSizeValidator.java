package org.kmnet.com.fw.common.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CharacterFixByteSizeValidator implements ConstraintValidator<CharacterFixByteSize, String> {

	private String encode;

	private int fix;

	@Override
	public void initialize(CharacterFixByteSize constraintAnnotation) {

		encode = constraintAnnotation.encode();
		fix = constraintAnnotation.fix();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null||"".equals(value)) {
			return true;
		}

		return CharacterValidator.validateFixByteNumber(value, encode, fix);
	}

}