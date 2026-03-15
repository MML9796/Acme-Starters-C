
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.realms.Sponsor;

@Service
public class SponsorDonationShowService extends AbstractService<Sponsor, Donation> {

	//Internal state
	@Autowired
	private SponsorDonationRepository	repository;
	private Donation					donation;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.donation = this.repository.findDonationById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int idDonation;
		int idS;
		idDonation = this.donation.getSponsorship().getSponsor().getId();
		idS = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = idDonation == idS;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.donation, "name", "notes", "money", "kind");
	}
}
