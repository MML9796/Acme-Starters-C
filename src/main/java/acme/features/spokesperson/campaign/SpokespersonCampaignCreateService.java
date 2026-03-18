
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignCreateService extends AbstractService<Spokesperson, Campaign> {

	//Internal state
	@Autowired
	private SpokespersonCampaignRepository	repository;
	private Campaign						campaign;


	//AbstractService interface
	@Override
	public void load() {
		Spokesperson spokesperson;
		spokesperson = (Spokesperson) super.getRequest().getPrincipal().getActiveRealm();
		this.campaign = super.newObject(Campaign.class);
		this.campaign.setDraftMode(true);
		this.campaign.setSpokesperson(spokesperson);
	}

	@Override
	public void authorise() {
		boolean status;
		String method;
		int spokespersonId, campaignId;
		Campaign ca;
		method = super.getRequest().getMethod();
		if (method.equals("GET"))
			status = true;
		else {
			spokespersonId = super.getRequest().getPrincipal().getActiveRealm().getId();
			campaignId = super.getRequest().getData("id", int.class);
			ca = this.repository.findCampaignById(campaignId);
			status = campaignId == 0 || ca != null && ca.getSpokesperson().getId() == spokespersonId;
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		Spokesperson spokesperson;
		spokesperson = (Spokesperson) super.getRequest().getPrincipal().getActiveRealm();
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		this.campaign.setSpokesperson(spokesperson);
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}

}
