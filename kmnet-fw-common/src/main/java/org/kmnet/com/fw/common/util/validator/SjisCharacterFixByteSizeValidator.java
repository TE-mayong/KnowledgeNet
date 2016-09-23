package org.kmnet.com.fw.common.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SjisCharacterFixByteSizeValidator implements ConstraintValidator<SjisCharacterFixByteSize, String> {

	private String encode;

	private int fix;

	@Override
	public void initialize(SjisCharacterFixByteSize constraintAnnotation) {

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