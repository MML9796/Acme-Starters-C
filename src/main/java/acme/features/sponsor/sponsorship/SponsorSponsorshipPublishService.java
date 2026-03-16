
package acme.features.sponsor.sponsorship;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
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
		sponsorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		sponsorshipId = super.getRequest().getData("id", int.class);
		spsh = this.repository.findSponsorshipById(sponsorshipId);
		status = spsh != null && spsh.getSponsor().getId() == sponsorId && spsh.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship);
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);
		Integer numDonations;
		Date publishMoment;

		numDonations = this.repository.getNumDonationsBySponsorshipId(this.sponsorship.getId());
		publishMoment = MomentHelper.getCurrentMoment();

		super.state(numDonations > 0, "*", "acme.validation.sponsorship.missing-donations.message");

		super.state(publishMoment.before(this.sponsorship.getStartMoment()), "startMoment", "acme.validation.sponsorship.publish-after-start.message");
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
