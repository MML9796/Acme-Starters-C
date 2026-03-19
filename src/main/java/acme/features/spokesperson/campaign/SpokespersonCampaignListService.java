
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignListService extends AbstractService<Spokesperson, Campaign> {

	//Internal state
	@Autowired
	private SpokespersonCampaignRepository	repository;
	private Collection<Campaign>			campaign;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.campaign = this.repository.findAllCampaignBySpokesperson(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = this.campaign.stream().allMatch(c -> c.getSpokesperson().getId() == id);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaign, "ticker", "name", "startMoment", "endMoment", "monthsActive", "effort", "moreInfo");
	}
}
