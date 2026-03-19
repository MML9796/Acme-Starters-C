
package acme.features.any.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;

@Service
public class AnySponsorshipShowService extends AbstractService<Any, Sponsorship> {

	//Internal state
	@Autowired
	private AnySponsorshipRepository	repository;
	private Sponsorship					sponsorship;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		if (this.sponsorship == null)
			status = false;
		else
			status = !this.sponsorship.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney");
		super.unbindGlobal("id", this.sponsorship.getId());
		super.unbindGlobal("sponsorId", this.sponsorship.getSponsor().getId());
	}

}
