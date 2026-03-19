
package acme.features.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.entities.sponsorship.Sponsorship;
import acme.features.sponsor.sponsorship.SponsorSponsorshipRepository;
import acme.realms.Sponsor;

@Service
public class SponsorDonationListService extends AbstractService<Sponsor, Donation> {

	//Internal state
	@Autowired
	private SponsorDonationRepository		repository;
	@Autowired
	private SponsorSponsorshipRepository	sponsorshipRepository;
	private Collection<Donation>			donation;


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

		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);

		int principalId = super.getRequest().getPrincipal().getActiveRealm().getId();

		var sponsorship = this.sponsorshipRepository.findSponsorshipById(sponsorshipId);

		if (sponsorship == null)
			status = false;
		else
			status = sponsorship.getSponsor().getId() == principalId;

		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		Sponsorship spsh;
		int id;
		id = super.getRequest().getData("sponsorshipId", int.class);
		spsh = this.sponsorshipRepository.findSponsorshipById(id);
		super.unbindObjects(this.donation, "name", "notes", "money", "kind");
		super.unbindGlobal("draftMode", spsh.getDraftMode());
		super.unbindGlobal("sponsorshipId", spsh.getId());
	}
}
