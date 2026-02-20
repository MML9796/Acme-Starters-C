
package acme.validators;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.strategy.Strategy;
import acme.entities.strategy.StrategyRepository;
import acme.validation.ValidStrategy;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	@Autowired
	private StrategyRepository repository;


	@Override
	public void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;

		if (strategy == null)
			result = true;
		else {

			if (strategy.getStartMoment() != null && strategy.getEndMoment() != null) {
				boolean fechasCorrectas = strategy.getEndMoment().after(strategy.getStartMoment());

				super.state(context, fechasCorrectas, "endMoment", "The end moment must be after the start moment");
			}

			if (!strategy.getDraftMode()) {
				boolean hasTactics = false;

				if (strategy.getId() != 0) {
					Integer count = this.repository.countTacticsByStrategyId(strategy.getId());
					hasTactics = count != null && count > 0;
				}

				super.state(context, hasTactics, "*", "Strategies cannot be published unless they have at least one tactic");
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
