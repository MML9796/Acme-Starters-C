
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;

@Service
public class AnyCampaignListService extends AbstractService<Any, Campaign> {

	//Internal state
	@Autowired
	private AnyCampaignRepository	repository;
	private Collection<Campaign>	campaign;


	//AbstractService interface
	@Override
	public void load() {
		this.campaign = this.repository.findAllCampaign();
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.campaign.stream().allMatch(c -> !c.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaign, "ticker", "name", "startMoment", "endMoment", "monthsActive", "effort", "moreInfo");
	}
}
