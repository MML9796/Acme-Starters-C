
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.milestones.Milestone;
import acme.entities.milestones.MilestoneKind;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneShowService extends AbstractService<Spokesperson, Milestone> {

	//Internal state
	@Autowired
	private SpokespersonMilestoneRepository	repository;
	private Milestone						milestone;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findMilestoneById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int idC;
		int idS;
		if (this.milestone == null)
			status = false;
		else {
			idC = this.milestone.getCampaign().getSpokesperson().getId();
			idS = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = idC == idS;
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		super.unbindGlobal("draftMode", this.milestone.getCampaign().getDraftMode());
		super.unbindGlobal("campaignId", this.milestone.getCampaign().getId());
		super.unbindGlobal("id", this.milestone.getId());
		SelectChoices opcionesKind = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}
}
