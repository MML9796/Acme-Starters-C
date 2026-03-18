
package acme.features.any.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.entities.tactic.Tactic;
import acme.features.any.strategy.AnyStrategyRepository;

@Service
public class AnyTacticListService extends AbstractService<Any, Tactic> {

	@Autowired
	private AnyTacticRepository		repository;

	@Autowired
	private AnyStrategyRepository	strategyRepository;

	private Collection<Tactic>		tactics;


	@Override
	public void load() {
		int strategyId;
		strategyId = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findTacticsByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		boolean status = false;
		int id = super.getRequest().getData("strategyId", int.class);
		Strategy st = this.strategyRepository.findOneStrategyById(id);
		if (st != null)
			status = !st.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "expectedPercentage", "kind");
	}
}
