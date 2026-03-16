
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.entities.part.Part.PartKind;
import acme.features.inventor.invention.InventorInventionRepository;
import acme.realms.Inventor;

@Service
public class InventorPartCreateService extends AbstractService<Inventor, Part> {

	//Internal state
	@Autowired
	private InventorPartRepository		repository;
	@Autowired
	private InventorInventionRepository	repositoryInvention;
	private Part						part;


	//AbstractService interface
	@Override
	public void load() {
		int inventionId;
		Invention inv;
		inventionId = super.getRequest().getData("inventionId", int.class);
		inv = this.repositoryInvention.findInventionById(inventionId);
		this.part = super.newObject(Part.class);
		if (inv != null)
			this.part.setInvention(inv);
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
			inventionId = super.getRequest().getData("inventionId", int.class);
			inv = this.repositoryInvention.findInventionById(inventionId);
			status = inv != null && inv.getInventor().getId() == inventorId && inv.getDraftMode();
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int id;
		Invention invention;
		id = super.getRequest().getData("inventionId", int.class);
		invention = this.repositoryInvention.findInventionById(id);
		super.bindObject(this.part, "name", "description", "cost", "kind");
		this.part.setInvention(invention);
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
		int id;
		id = super.getRequest().getData("inventionId", int.class);
		super.unbindObject(this.part, "name", "description", "cost", "kind");
		super.unbindGlobal("inventionId", id);
		SelectChoices opcionesKind = SelectChoices.from(PartKind.class, this.part.getKind());
		super.unbindGlobal("listaKinds", opcionesKind);
	}

}
