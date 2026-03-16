
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.tactic.Tactic;
import acme.entities.tactic.TacticKind;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticUpdateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;
	private Tactic						tactic;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int fundraiserId, tacticId;
		Tactic t;
		fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		tacticId = super.getRequest().getData("id", int.class);
		t = this.repository.findTacticById(tacticId);
		status = t != null && t.getStrategy().getFundraiser().getId() == fundraiserId && t.getStrategy().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "expectedPercentage", "kind", "notes");
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, "name", "expectedPercentage", "kind", "notes");
		SelectChoices opcionesKind = SelectChoices.from(TacticKind.class, this.tactic.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}
}
