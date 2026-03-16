
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.entities.tactic.Tactic;
import acme.features.fundraiser.tactic.FundraiserTacticRepository;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyDeleteService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;
	@Autowired
	private FundraiserTacticRepository		tacticRepository;
	private Strategy						strategy;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int fundraiserId, strategyId;
		Strategy st;
		fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		strategyId = super.getRequest().getData("id", int.class);
		st = this.repository.findStrategyById(strategyId);
		status = st != null && st.getFundraiser().getId() == fundraiserId && st.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy);
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);
	}

	@Override
	public void execute() {
		Collection<Tactic> tactics;
		int id;
		id = super.getRequest().getData("id", int.class);
		tactics = this.tacticRepository.findAllTacticByStrategyId(id);
		tactics.stream().forEach(t -> this.tacticRepository.delete(t));
		this.repository.delete(this.strategy);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}
}
