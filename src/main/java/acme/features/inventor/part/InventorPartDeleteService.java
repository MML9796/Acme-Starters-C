
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.part.Part;
import acme.realms.Inventor;

@Service
public class InventorPartDeleteService extends AbstractService<Inventor, Part> {

	//Internal state
	@Autowired
	private InventorPartRepository	repository;
	private Part					part;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int inventorId, partId;
		Part p;
		inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		partId = super.getRequest().getData("id", int.class);
		p = this.repository.findPartById(partId);
		status = p != null && p.getInvention().getInventor().getId() == inventorId && p.getInvention().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part);
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
	}

	@Override
	public void execute() {
		this.repository.delete(this.part);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");

	}

}
