
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
	private Invention					invention;


	//AbstractService interface
	@Override
	public void load() {
		int inventionId;
		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repositoryInvention.findInventionById(inventionId);
		this.part = super.newObject(Part.class);
		if (this.invention != null)
			this.part.setInvention(this.invention);
	}

	@Override
	public void authorise() {
		boolean status;
		int inventorId, inventionId;
		Invention inv;
		inventionId = super.getRequest().getData("inventionId", int.class);
		inv = this.repositoryInvention.findInventionById(inventionId);
		inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = inv != null && inv.getInventor().getId() == inventorId && inv.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
		this.part.setInvention(this.invention);
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
		super.unbindGlobal("inventionId", this.invention.getId());
	}

}
