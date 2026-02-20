
package acme.validators;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.CampaignRepository;
import acme.validation.ValidCampaign;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	@Autowired
	private CampaignRepository repository;


	@Override
	protected void initialise(final ValidCampaign annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;
		if (campaign == null)
			result = true;
		else {
			if (!campaign.getDraftMode()) {
				boolean oneMilestone;
				Integer totalMilestones = this.repository.totalMilestoneByCamapaignId(campaign.getId());
				oneMilestone = totalMilestones == null || totalMilestones >= 1;
				super.state(context, oneMilestone, "milestone", "you can't be published unless they have at least one milestone ");

				boolean timeCorrect;
				timeCorrect = campaign.getEndMoment().after(campaign.getStartMoment());
				super.state(context, timeCorrect, "endMoment", "you can't be published whit an invalid time interval ");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}
