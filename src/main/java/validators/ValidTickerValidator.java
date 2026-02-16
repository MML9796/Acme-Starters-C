
package validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import validation.ValidTicker;

public class ValidTickerValidator implements ConstraintValidator<ValidTicker, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (value == null)
			return false;

		return value.matches("^[A-Z]{2}[0-9]{2}-\\w{5,10}$");
	}
}
