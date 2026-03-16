
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.features.inventor.invention.InventorInventionRepository;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	//Internal state
	@Autowired
	private InventorPartRepository		repository;
	@Autowired
	private InventorInventionRepository	inventionRepository;
	private Collection<Part>			part;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("inventionId", int.class);
		this.part = this.repository.findAllPartByInventionId(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id = super.getRequest().getData("inventionId", int.class);
		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = this.inventionRepository.findInventionById(id).getInventor().getId() == inventorId;
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		Invention inv;
		int id;
		id = super.getRequest().getData("inventionId", int.class);
		inv = this.inventionRepository.findInventionById(id);
		super.unbindObjects(this.part, "name", "cost");
		super.unbindGlobal("inventionId", id);
		super.unbindGlobal("draftMode", inv.getDraftMode());
		super.unbindGlobal("invId", inv.getId());
	}
}
