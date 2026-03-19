
package acme.features.spokesperson.campaign;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.features.spokesperson.milestone.SpokespersonMilestoneRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	//Internal state
	@Autowired
	private SpokespersonCampaignRepository	repository;
	@Autowired
	private SpokespersonMilestoneRepository	milestoneRepository;
	private Campaign						campaign;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int spokespersonId, campaignId;
		Campaign ca;
		spokespersonId = super.getRequest().getPrincipal().getActiveRealm().getId();
		campaignId = super.getRequest().getData("id", int.class);

		ca = this.repository.findCampaignById(campaignId);
		status = ca != null && ca.getSpokesperson().getId() == spokespersonId && ca.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign);
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
		if (!super.getErrors().hasErrors()) {
			Double m;
			m = this.milestoneRepository.countAllMilestoneByCampaignId(this.campaign.getId());
			Boolean haveMilestone;
			haveMilestone = m > 0;
			super.state(haveMilestone, "*", "acme.publish.campaign.noHaveMilestone.message");
			Date mo;
			mo = MomentHelper.getCurrentMoment();
			Boolean validStartMoment;
			validStartMoment = this.campaign.getStartMoment().after(mo);
			super.state(validStartMoment, "*", "acme.publish.campaign.validStartMoment.message");

		}
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}

}
