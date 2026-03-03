
package acme.validators;

import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import acme.client.components.datatypes.Money;
import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.PropertyHelper;
import acme.internals.helpers.HibernateHelper;
import acme.validation.ValidMoney2;

@Validator
public class MoneyValidator2 extends AbstractValidator<ValidMoney2, Money> {

	// Internal state ---------------------------------------------------------

	private double	lowerLimit;
	private double	upperLimit;

	// Initialiser ------------------------------------------------------------


	@Override
	public void initialise(final ValidMoney2 annotation) {
		assert annotation != null;

		this.lowerLimit = annotation.min();
		if (Double.isNaN(this.lowerLimit))
			this.lowerLimit = PropertyHelper.getRequiredProperty("acme.data.money.minimum", double.class);

		this.upperLimit = annotation.max();
		if (Double.isNaN(this.upperLimit))
			this.upperLimit = PropertyHelper.getRequiredProperty("acme.data.money.maximum", double.class);
	}

	// AbstractValidator interface --------------------------------------------
	@Override
	public boolean isValid(final Money value, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;
		HibernateConstraintValidatorContext hibernateContext;

		hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);

		if (value == null)
			result = true;
		else {
			result = this.lowerLimit <= value.getAmount() && value.getAmount() <= this.upperLimit;
			if (!result)
				HibernateHelper.replaceParameter(hibernateContext, "placeholder", "acme.validation.money.range.message", this.lowerLimit, this.upperLimit);

			boolean isEur = true;
			String currency = value.getCurrency();
			isEur = currency.equals("EUR");
			super.state(context, isEur, "*", "acme.validation.money.eur-currency.message");

		}

		result = !super.hasErrors(context);

		return result;
	}
}
