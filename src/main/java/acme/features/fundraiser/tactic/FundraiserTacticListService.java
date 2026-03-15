
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
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
		int id = super.getRequest().getData("strategyId", int.class);
		int accountId = super.getRequest().getPrincipal().getAccountId();
		status = this.strategyRepository.findStrategyById(id).getFundraiser().getUserAccount().getId() == accountId;
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "expectedPercentage", "kind");
	}
}
