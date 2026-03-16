
package acme.features.any.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.donation.Donation;
import acme.entities.donation.DonationKind;

@Service
public class AnyDonationShowService extends AbstractService<Any, Donation> {

	//Internal state
	@Autowired
	private AnyDonationRepository	repository;
	private Donation				donation;


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
		if (this.donation == null)
			status = false;
		else
			status = !this.donation.getSponsorship().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.donation, "name", "notes", "money", "kind");
		SelectChoices opcionesKind = SelectChoices.from(DonationKind.class, this.donation.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}

}
