
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	//Internal state
	@Autowired
	private AnySponsorshipRepository	repository;
	private Collection<Sponsorship>		sponsorship;


	//AbstractService interface
	@Override
	public void load() {
		this.sponsorship = this.repository.findAllSponsorship();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sponsorship, "ticker", "name", "startMoment", "endMoment");
	}

}
