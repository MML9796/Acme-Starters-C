
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.entities.tactic.Tactic;
import acme.entities.tactic.TacticKind;
import acme.features.fundraiser.strategy.FundraiserStrategyRepository;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository		repository;
	@Autowired
	private FundraiserStrategyRepository	repositoryStrategy;
	private Tactic							tactic;


	@Override
	public void load() {
		int strategyId;
		Strategy s;
		strategyId = super.getRequest().getData("strategyId", int.class);
		s = this.repositoryStrategy.findStrategyById(strategyId);
		this.tactic = super.newObject(Tactic.class);
		this.tactic.setStrategy(s);
	}

	@Override
	public void authorise() {
		boolean status;
		String method;
		int fundraiserId, strategyId;
		Strategy s;
		method = super.getRequest().getMethod();
		if (method.equals("GET"))
			status = true;
		else {
			fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
			strategyId = super.getRequest().getData("strategyId", int.class);
			s = this.repositoryStrategy.findStrategyById(strategyId);
			status = s != null && s.getId() == strategyId && s.getFundraiser().getId() == fundraiserId && s.getDraftMode();
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int id;
		Strategy strategy;
		id = super.getRequest().getData("strategyId", int.class);
		strategy = this.repositoryStrategy.findStrategyById(id);
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		this.tactic.setStrategy(strategy);
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
		int id;
		id = super.getRequest().getData("strategyId", int.class);
		super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		super.unbindGlobal("strategyId", id);
		SelectChoices opcionesKind = SelectChoices.from(TacticKind.class, this.tactic.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}

}
