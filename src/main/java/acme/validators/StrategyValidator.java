
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

			boolean uniqueStrategy;
			Strategy existingStrategy;

			existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
			uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

			super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.duplicated-ticker.message");

			if (strategy.getStartMoment() != null && strategy.getEndMoment() != null) {
				boolean correctDates = strategy.getEndMoment().after(strategy.getStartMoment());

				super.state(context, correctDates, "endMoment", "acme.validation.strategy.invalid-dates.message");
			}

			if (!strategy.getDraftMode()) {
				boolean hasTactics = false;

				if (strategy.getId() != 0) {
					Integer count = this.repository.countTacticsByStrategyId(strategy.getId());
					hasTactics = count != null && count > 0;
				}

				super.state(context, hasTactics, "*", "acme.validation.strategy.missing-tactics.message");
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
