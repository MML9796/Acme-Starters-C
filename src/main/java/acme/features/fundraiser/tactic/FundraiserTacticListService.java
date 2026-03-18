
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.entities.tactic.Tactic;
import acme.features.fundraiser.strategy.FundraiserStrategyRepository;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticListService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository		repository;
	@Autowired
	private FundraiserStrategyRepository	strategyRepository;
	private Collection<Tactic>				tactics;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findAllTacticByStrategyId(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id, strategyId;
		Strategy s;
		strategyId = super.getRequest().getData("strategyId", int.class);
		s = this.strategyRepository.findStrategyById(strategyId);
		if (s == null)
			status = false;
		else {
			id = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = this.tactics.stream().allMatch(m -> m.getStrategy().getFundraiser().getId() == id);

		}
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		Strategy s;
		int id;
		id = super.getRequest().getData("strategyId", int.class);
		s = this.strategyRepository.findStrategyById(id);
		super.unbindObjects(this.tactics, "name", "expectedPercentage", "kind");
		super.unbindGlobal("draftMode", s.getDraftMode());
		super.unbindGlobal("strategyId", s.getId());
	}
}
