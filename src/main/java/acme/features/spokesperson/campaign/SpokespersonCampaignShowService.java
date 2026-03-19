
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignShowService extends AbstractService<Spokesperson, Campaign> {

	//Internal state
	@Autowired
	private SpokespersonCampaignRepository	repository;
	private Campaign						campaign;


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
		int idC;
		int idS;
		if (this.campaign == null)
			status = false;
		else {
			idC = this.campaign.getSpokesperson().getId();
			idS = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = idC == idS;
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "monthsActive", "effort", "moreInfo", "draftMode");
		super.unbindGlobal("id", this.campaign.getId());
		super.unbindGlobal("spokespersonId", this.campaign.getSpokesperson().getId());
	}
}
