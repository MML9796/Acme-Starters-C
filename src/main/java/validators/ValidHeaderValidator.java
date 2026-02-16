
package validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import validation.ValidHeader;

public class ValidHeaderValidator implements ConstraintValidator<ValidHeader, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty())
			return false;

		int length = value.trim().length();
		return length >= 1 && length <= 75;
	}
}
