
package acme.features.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.realms.Sponsor;

@Service
public class SponsorDonationListService extends AbstractService<Sponsor, Donation> {

	//Internal state
	@Autowired
	private SponsorDonationRepository	repository;
	private Collection<Donation>		donation;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("sponsorshipId", int.class);
		this.donation = this.repository.findAllDonationBySponsorshipId(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = this.donation.stream().allMatch(d -> d.getSponsorship().getSponsor().getId() == id);
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.donation, "name", "notes");
	}
}
