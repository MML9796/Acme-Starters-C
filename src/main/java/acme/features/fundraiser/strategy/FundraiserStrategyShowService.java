
package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyShowService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;
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
		int id = super.getRequest().getData("id", int.class);
		int accountId = super.getRequest().getPrincipal().getAccountId();
		status = this.repository.findStrategyById(id).getFundraiser().getUserAccount().getId() == accountId;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage");
		super.unbindGlobal("id", this.strategy.getId());
		super.unbindGlobal("fundraiserId", this.strategy.getFundraiser().getId());
	}
}
