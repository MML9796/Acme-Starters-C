
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	//Internal state
	@Autowired
	private SponsorSponsorshipRepository	repository;
	private Sponsorship						sponsorship;


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

		int id = super.getRequest().getData("id", int.class);
		Sponsorship s = this.repository.findSponsorshipById(id);

		if (s != null) {
			int idS = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = s.getSponsor().getId() == idS;
		} else
			status = false;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "monthsActive", "totalMoney", "moreInfo", "draftMode");
		super.unbindGlobal("id", this.sponsorship.getId());
		super.unbindGlobal("sponsorId", this.sponsorship.getSponsor().getId());
	}
}
