
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.milestones.Milestone;
import acme.entities.milestones.MilestoneKind;
import acme.features.spokesperson.campaign.SpokespersonCampaignRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneCreateService extends AbstractService<Spokesperson, Milestone> {

	//Internal state
	@Autowired
	private SpokespersonMilestoneRepository	repository;
	@Autowired
	private SpokespersonCampaignRepository	repositoryCampaign;
	private Milestone						milestone;


	//AbstractService interface
	@Override
	public void load() {
		int campaignId;
		Campaign c;
		campaignId = super.getRequest().getData("campaignId", int.class);
		c = this.repositoryCampaign.findCampaignById(campaignId);
		this.milestone = super.newObject(Milestone.class);
		this.milestone.setCampaign(c);
	}

	@Override
	public void authorise() {
		boolean status;
		String method;
		int spokespersonId, campaignId;
		Campaign c;
		method = super.getRequest().getMethod();
		campaignId = super.getRequest().getData("campaignId", int.class);
		c = this.repositoryCampaign.findCampaignById(campaignId);
		if (method.equals("GET"))
			status = c != null;
		else {
			spokespersonId = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = c != null && c.getId() == campaignId && c.getSpokesperson().getId() == spokespersonId && c.getDraftMode();
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int id;
		Campaign campaign;
		id = super.getRequest().getData("campaignId", int.class);
		campaign = this.repositoryCampaign.findCampaignById(id);
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
		this.milestone.setCampaign(campaign);
	}

	@Override
	public void validate() {
		super.validateObject(this.milestone);
	}

	@Override
	public void execute() {
		this.repository.save(this.milestone);
	}

	@Override
	public void unbind() {
		int id;
		id = super.getRequest().getData("campaignId", int.class);
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		super.unbindGlobal("campaignId", id);
		SelectChoices opcionesKind = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}

}
