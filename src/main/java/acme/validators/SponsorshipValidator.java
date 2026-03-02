
package acme.validators;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.SponsorshipRepository;
import acme.validation.ValidSponsorship;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	@Autowired
	private SponsorshipRepository repository;


	@Override
	public void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		assert context != null;

		boolean resultado;

		if (sponsorship == null)
			resultado = true;
		else {

			if (!sponsorship.getDraftMode()) {

				boolean uniqueSponsorship;
				Sponsorship existingSponsorship;

				existingSponsorship = this.repository.findSponsorshipByTicker(sponsorship.getTicker());
				uniqueSponsorship = existingSponsorship == null || existingSponsorship.equals(sponsorship);

				super.state(context, uniqueSponsorship, "ticker", "acme.validation.sponsorship.duplicated-ticker.message");

				boolean correctDate;
				correctDate = sponsorship.getEndMoment().after(sponsorship.getStartMoment());

				super.state(context, correctDate, "endMoment", "acme.validation.sponsorship.invalid-date.message");

				boolean atLeastOneDonation = true;
				int existingDonations;

				existingDonations = this.repository.findDonationsSizeBySponsorshipId(sponsorship.getId());
				atLeastOneDonation = existingDonations >= 1;

				super.state(context, atLeastOneDonation, "*", "acme.validation.sponsorship.missing-donations.message");
			}

			resultado = !super.hasErrors(context);
		}

		return resultado;
	}
}
