
package acme.features.any.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	//Internal state
	@Autowired
	private AnyInventionRepository	repository;
	private Invention				invention;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;
		if (this.invention != null)
			status = !this.invention.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "monthsActive", "cost", "moreInfo");
		super.unbindGlobal("id", this.invention.getId());
		super.unbindGlobal("inventorId", this.invention.getInventor().getId());
	}
}
