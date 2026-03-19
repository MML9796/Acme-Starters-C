
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.entities.donation.DonationKind;
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

		int donationId = super.getRequest().getData("id", int.class);
		Donation d = this.repository.findDonationById(donationId);
		int principalId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (d == null)
			status = false;
		else
			status = d.getSponsorship().getSponsor().getId() == principalId;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.donation, "name", "notes", "money", "kind");
		super.unbindGlobal("draftMode", this.donation.getSponsorship().getDraftMode());
		super.unbindGlobal("sponsorshipId", this.donation.getSponsorship().getId());
		super.unbindGlobal("id", this.donation.getId());
		SelectChoices opcionesKind = SelectChoices.from(DonationKind.class, this.donation.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}
}
