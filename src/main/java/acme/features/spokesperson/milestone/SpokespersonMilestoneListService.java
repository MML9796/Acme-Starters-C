
package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.milestones.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneListService extends AbstractService<Spokesperson, Milestone> {

	//Internal state
	@Autowired
	private SpokespersonMilestoneRepository	repository;
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
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = this.milestone.stream().allMatch(m -> m.getCampaign().getSpokesperson().getId() == id);
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.milestone, "title", "achievements");
	}
}
