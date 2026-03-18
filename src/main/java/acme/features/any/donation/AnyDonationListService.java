
package acme.features.any.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.features.any.sponsorship.AnySponsorshipRepository;

@Service
public class AnyDonationListService extends AbstractService<Any, Donation> {

	//Internal state
	@Autowired
	private AnyDonationRepository		repository;
	@Autowired
	private AnySponsorshipRepository	repoSponsorship;
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

		int id = super.getRequest().getData("sponsorshipId", int.class);
		status = !this.repoSponsorship.findSponsorshipById(id).getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.donation, "name", "notes");
	}

}
