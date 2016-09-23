package org.kmnet.com.fw.common.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CharacterFormatValidator implements ConstraintValidator<CharacterFormat, String> {

	private String[] checkType = null;

	@Override
	public void initialize(CharacterFormat constraintAnnotation) {

		checkType = constraintAnnotation.checkType();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null||"".equals(value)) {
			return true;
		}
		return CharacterValidator.characterValid(value, checkType);
	}

}