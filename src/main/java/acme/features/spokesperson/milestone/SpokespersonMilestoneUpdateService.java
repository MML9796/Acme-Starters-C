
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.milestones.Milestone;
import acme.entities.milestones.MilestoneKind;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneUpdateService extends AbstractService<Spokesperson, Milestone> {

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
		int spokespersonId, milestoneId;
		Milestone m;
		spokespersonId = super.getRequest().getPrincipal().getActiveRealm().getId();
		milestoneId = super.getRequest().getData("id", int.class);
		m = this.repository.findMilestoneById(milestoneId);
		status = m != null && m.getCampaign().getSpokesperson().getId() == spokespersonId && m.getCampaign().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
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
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		SelectChoices opcionesKind = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);

	}

}
