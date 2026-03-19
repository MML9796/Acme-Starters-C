
package acme.features.fundraiser.strategy;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.features.fundraiser.tactic.FundraiserTacticRepository;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyPublishService extends AbstractService<Fundraiser, Strategy> {

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
		if (!super.getErrors().hasErrors()) {
			int t;
			t = this.tacticRepository.findNumberTacticByStrategyId(this.strategy.getId());
			Boolean haveTactic;
			haveTactic = t > 0;
			super.state(haveTactic, "*", "acme.publish.campaign.noHaveTactic.message");
			Date mo;
			mo = MomentHelper.getCurrentMoment();
			Boolean validStartMoment;
			validStartMoment = this.strategy.getStartMoment().after(mo);
			super.state(validStartMoment, "*", "acme.publish.campaign.validStartMoment.message");

		}
	}

	@Override
	public void execute() {
		this.strategy.setDraftMode(false);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}

}
