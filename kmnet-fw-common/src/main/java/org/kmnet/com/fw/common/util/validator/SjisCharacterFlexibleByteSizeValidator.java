package org.kmnet.com.fw.common.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SjisCharacterFlexibleByteSizeValidator implements ConstraintValidator<SjisCharacterFlexibleByteSize, String> {

	private int max;

	private int min;

	private String encode;

	@Override
	public void initialize(SjisCharacterFlexibleByteSize constraintAnnotation) {

		max = constraintAnnotation.max();
		min = constraintAnnotation.min();
		encode = constraintAnnotation.encode();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null||"".equals(value)) {
			return true;
		}

		return CharacterValidator.validateFlexibleByteNumber(value, encode, min, max);

	}

}