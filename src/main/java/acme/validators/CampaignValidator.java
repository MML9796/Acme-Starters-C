
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
				{
					boolean oneMilestone;
					Integer totalMilestones = this.repository.totalMilestoneByCamapaignId(campaign.getId());
					oneMilestone = totalMilestones == null || totalMilestones >= 1;
					super.state(context, oneMilestone, "milestone", "acme.validation.campaign.one-milestone.message");

				}
				{
					if (campaign.getEndMoment() != null && campaign.getStartMoment() != null) {
						boolean timeCorrect;
						timeCorrect = campaign.getEndMoment().after(campaign.getStartMoment());
						super.state(context, timeCorrect, "endMoment", "acme.validation.campaign.invalid-time-interval.message ");

					}
				}
				{
					boolean uniqueCampaing;
					Campaign existingCampaing;

					existingCampaing = this.repository.findCampaignByTicker(campaign.getTicker());
					uniqueCampaing = existingCampaing == null || existingCampaing.equals(campaign);

					super.state(context, uniqueCampaing, "ticker", "acme.validation.campaign.duplicated-ticker.message");

				}
			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}
