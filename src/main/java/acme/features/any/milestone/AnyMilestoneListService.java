
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.milestones.Milestone;
import acme.features.any.campaign.AnyCampaignRepository;

@Service
public class AnyMilestoneListService extends AbstractService<Any, Milestone> {

	//Internal state
	@Autowired
	private AnyMilestoneRepository	repository;
	@Autowired
	private AnyCampaignRepository	campaign;
	private Collection<Milestone>	milestone;


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
		int id;
		Campaign c;

		id = super.getRequest().getData("campaignId", int.class);
		c = this.campaign.findCampaignById(id);

		if (c == null)
			status = false;
		else
			status = !c.getDraftMode();
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.milestone, "title", "achievements", "effort", "kind");
	}
}
