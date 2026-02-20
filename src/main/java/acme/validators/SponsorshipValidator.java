
package acme.validators;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.donation.Donation;
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

				boolean fechaCorrecta;
				fechaCorrecta = sponsorship.getEndMoment().after(sponsorship.getStartMoment());

				super.state(context, fechaCorrecta, "endMoment", "startMoment/endMoment must be a valid time interval in future");

				boolean hasDonation = true;

				if (sponsorship.getId() != 0) {
					List<Donation> donations = this.repository.findDonationBySponsorshipId(sponsorship.getId());
					hasDonation = !donations.isEmpty();
				}

				super.state(context, hasDonation, "*", "Sponsorship cannot be published unless they have at least one donation");
			}

			resultado = !super.hasErrors(context);
		}

		return resultado;
	}
}
