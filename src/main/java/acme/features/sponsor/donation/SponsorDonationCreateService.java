
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.entities.donation.DonationKind;
import acme.entities.sponsorship.Sponsorship;
import acme.features.sponsor.sponsorship.SponsorSponsorshipRepository;
import acme.realms.Sponsor;

@Service
public class SponsorDonationCreateService extends AbstractService<Sponsor, Donation> {

	//Internal state
	@Autowired
	private SponsorDonationRepository		repository;
	@Autowired
	private SponsorSponsorshipRepository	sponsorshipRepository;
	private Donation						donation;


	//AbstractService interface
	@Override
	public void load() {
		int sponsorshipId;
		Sponsorship spsh;
		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		spsh = this.sponsorshipRepository.findSponsorshipById(sponsorshipId);
		this.donation = super.newObject(Donation.class);
		this.donation.setSponsorship(spsh);
		if (spsh != null)
			this.donation.setSponsorship(spsh);
	}

	@Override
	public void authorise() {
		boolean status;

		String method = super.getRequest().getMethod();
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		Sponsorship spsh = this.sponsorshipRepository.findSponsorshipById(sponsorshipId);

		if (method.equals("GET"))
			status = spsh != null;
		else {
			int sponsorId = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = spsh != null && spsh.getId() == sponsorshipId && spsh.getSponsor().getId() == sponsorId && spsh.getDraftMode();
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int id;
		Sponsorship sponsorship;
		id = super.getRequest().getData("sponsorshipId", int.class);
		sponsorship = this.sponsorshipRepository.findSponsorshipById(id);
		super.bindObject(this.donation, "name", "notes", "money", "kind");
		this.donation.setSponsorship(sponsorship);
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
		int id;
		id = super.getRequest().getData("sponsorshipId", int.class);
		super.unbindObject(this.donation, "name", "notes", "money", "kind");
		super.unbindGlobal("sponsorshipId", id);
		SelectChoices opcionesKind = SelectChoices.from(DonationKind.class, this.donation.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}
}
