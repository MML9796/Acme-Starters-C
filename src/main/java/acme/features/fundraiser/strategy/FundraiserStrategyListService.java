
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyListService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;
	private Collection<Strategy>			strategies;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategies = this.repository.findAllStrategyByFundraiserId(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = this.strategies.stream().allMatch(c -> c.getFundraiser().getId() == id);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, "ticker", "name", "startMoment", "endMoment");
	}
}
