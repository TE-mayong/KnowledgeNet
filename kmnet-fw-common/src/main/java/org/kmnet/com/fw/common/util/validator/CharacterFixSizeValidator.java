package org.kmnet.com.fw.common.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CharacterFixSizeValidator implements ConstraintValidator<CharacterFixSize, String> {

	private int fix;

	@Override
	public void initialize(CharacterFixSize constraintAnnotation) {

		fix = constraintAnnotation.fix();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null||"".equals(value)) {
			return true;
		}

		return CharacterValidator.validateFixNumber(value, fix);
	}

}