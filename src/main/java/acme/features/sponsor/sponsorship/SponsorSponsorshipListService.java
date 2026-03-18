
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipListService extends AbstractService<Sponsor, Sponsorship> {

	//Internal state
	@Autowired
	private SponsorSponsorshipRepository	repository;
	private Collection<Sponsorship>			sponsorship;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.sponsorship = this.repository.findAllSponsorshipBySponsor(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = this.sponsorship.stream().allMatch(s -> s.getSponsor().getId() == id);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}
}
