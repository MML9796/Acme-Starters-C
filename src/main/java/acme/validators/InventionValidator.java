
package acme.validators;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.invention.Invention;
import acme.entities.invention.InventionRepository;
import acme.validation.ValidInvention;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	@Autowired
	private InventionRepository repository;


	@Override
	public void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (invention == null)
			result = true;
		else {

			if (!invention.getDraftMode()) {
				{
					boolean uniqueInvention;
					Invention existingInvention;
					existingInvention = this.repository.findInventionByTicker(invention.getTicker());
					uniqueInvention = existingInvention == null || existingInvention.equals(invention);
					super.state(context, uniqueInvention, "ticker", "acme.validation.invention.duplicated-ticker.message");
				}

				{
					if (invention.getEndMoment() != null && invention.getStartMoment() != null) {
						boolean correctDates;
						correctDates = invention.getEndMoment().after(invention.getStartMoment());
						super.state(context, correctDates, "endMoment", "acme.validation.invention.invalid-dates.message");
					}
				}

				{
					boolean hasParts = true;
					Integer parts = this.repository.findPartsByInventionId(invention.getId());
					hasParts = parts > 0 && parts != null;
					super.state(context, hasParts, "*", "acme.validation.invention.existing-part.message");
				}
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
