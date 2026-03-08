
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.milestones.Milestone;

@Service
public class AnyMilestoneListService extends AbstractService<Any, Milestone> {

	//Internal state
	@Autowired
	private AnyMilestoneRepository	repository;
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
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.milestone, "title", "achievements");
	}
}
