
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.entities.donation.DonationKind;
import acme.realms.Sponsor;

@Service
public class SponsorDonationUpdateService extends AbstractService<Sponsor, Donation> {

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
		int sponsorId, id;
		Donation d;
		sponsorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		id = super.getRequest().getData("id", int.class);
		d = this.repository.findDonationById(id);
		status = d != null && d.getSponsorship().getSponsor().getId() == sponsorId && d.getSponsorship().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.donation);
	}

	@Override
	public void execute() {
		this.repository.save(this.donation);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.donation, "name", "notes", "money", "kind");
		SelectChoices opcionesKind = SelectChoices.from(DonationKind.class, this.donation.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
		super.unbindGlobal("draftMode", this.donation.getSponsorship().getDraftMode());
		super.unbindGlobal("id", this.donation.getId());

	}
}
