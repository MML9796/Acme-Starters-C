
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.entities.sponsorship.Sponsorship;
import acme.features.sponsor.donation.SponsorDonationRepository;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	//Internal state
	@Autowired
	private SponsorSponsorshipRepository	repository;
	@Autowired
	private SponsorDonationRepository		donationRepository;
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
		int sponsorId, sponsorshipId;
		Sponsorship spsh;
		Collection<Donation> d;
		sponsorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		sponsorshipId = super.getRequest().getData("id", int.class);
		d = this.donationRepository.findAllDonationBySponsorshipId(sponsorshipId);
		spsh = this.repository.findSponsorshipById(sponsorshipId);
		status = spsh != null && spsh.getSponsor().getId() == sponsorId && spsh.getDraftMode() && !d.isEmpty();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship);
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);
	}

	@Override
	public void execute() {
		this.sponsorship.setDraftMode(false);
		this.repository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}
}
