
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.tactic.Tactic;
import acme.entities.tactic.TacticKind;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticShowService extends AbstractService<Fundraiser, Tactic> {

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
		if (this.tactic == null)
			super.setAuthorised(false);
		else {
			int idS;
			int idF;
			idS = this.tactic.getStrategy().getFundraiser().getId();
			idF = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = idS == idF;
			super.setAuthorised(status);
		}
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, "name", "expectedPercentage", "kind", "notes");
		super.unbindGlobal("draftMode", this.tactic.getStrategy().getDraftMode());
		super.unbindGlobal("strategyId", this.tactic.getStrategy().getId());
		super.unbindGlobal("id", this.tactic.getId());
		SelectChoices opcionesKind = SelectChoices.from(TacticKind.class, this.tactic.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}
}
