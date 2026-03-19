
package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.milestones.Milestone;
import acme.features.spokesperson.campaign.SpokespersonCampaignRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneListService extends AbstractService<Spokesperson, Milestone> {

	//Internal state
	@Autowired
	private SpokespersonMilestoneRepository	repository;
	@Autowired
	private SpokespersonCampaignRepository	campaignRepository;
	private Collection<Milestone>			milestone;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("campaignId", int.class);
		this.milestone = this.repository.findAllMilestoneByCampaignId(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id, campaignId;
		Campaign c;
		campaignId = super.getRequest().getData("campaignId", int.class);
		c = this.campaignRepository.findCampaignById(campaignId);
		if (c == null)
			status = false;
		else {
			id = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = this.milestone.stream().allMatch(m -> m.getCampaign().getSpokesperson().getId() == id);

		}
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		Campaign c;
		int id;
		id = super.getRequest().getData("campaignId", int.class);
		c = this.campaignRepository.findCampaignById(id);
		super.unbindObjects(this.milestone, "title", "achievements");
		super.unbindGlobal("draftMode", c.getDraftMode());
		super.unbindGlobal("campaignId", c.getId());
	}
}
