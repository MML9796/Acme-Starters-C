
package acme.features.any.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;

@Service
public class AnyCampaignShowService extends AbstractService<Any, Campaign> {

	//Internal state
	@Autowired
	private AnyCampaignRepository	repository;
	private Campaign				campaign;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		if (this.campaign == null)
			status = false;
		else
			status = !this.campaign.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "monthsActive", "effort", "moreInfo");
		super.unbindGlobal("id", this.campaign.getId());
		super.unbindGlobal("spokespersonId", this.campaign.getSpokesperson().getId());
	}
}
