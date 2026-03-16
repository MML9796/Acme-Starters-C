
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionCreateService extends AbstractService<Inventor, Invention> {

	//Internal state
	@Autowired
	private InventorInventionRepository	repository;
	private Invention					invention;


	//AbstractService interface
	@Override
	public void load() {
		Inventor inventor;
		inventor = (Inventor) super.getRequest().getPrincipal().getActiveRealm();
		this.invention = super.newObject(Invention.class);
		this.invention.setDraftMode(true);
		this.invention.setInventor(inventor);
	}

	@Override
	public void authorise() {
		boolean status;
		String method;
		int inventorId, inventionId;
		Invention inv;
		method = super.getRequest().getMethod();
		if (method.equals("GET"))
			status = true;
		else {
			inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
			inventionId = super.getRequest().getData("id", int.class);
			inv = this.repository.findInventionById(inventionId);
			status = inventionId == 0 || inv != null && inv.getInventor().getId() == inventorId;
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		Inventor inventor;
		inventor = (Inventor) super.getRequest().getPrincipal().getActiveRealm();
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		this.invention.setInventor(inventor);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}

}
