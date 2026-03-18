
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.part.Part;
import acme.entities.part.Part.PartKind;
import acme.realms.Inventor;

@Service
public class InventorPartUpdateService extends AbstractService<Inventor, Part> {

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
		int inventorId, id;
		Part p;
		id = super.getRequest().getData("id", int.class);
		p = this.repository.findPartById(id);
		inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = p != null && p.getInvention().getInventor().getId() == inventorId && p.getInvention().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");
		SelectChoices opcionesKind = SelectChoices.from(PartKind.class, this.part.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
		super.unbindGlobal("draftMode", this.part.getInvention().getDraftMode());
		super.unbindGlobal("id", this.part.getId());

	}

}
