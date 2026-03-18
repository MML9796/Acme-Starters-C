
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
	private Invention					invention;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("inventionId", int.class);
		this.part = this.repository.findAllPartByInventionId(id);
		this.invention = this.inventionRepository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;
		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		if (this.invention != null)
			status = this.invention.getInventor().getId() == inventorId;
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.part, "name", "description", "cost", "kind");
		super.unbindGlobal("inventionId", this.invention.getId());
		super.unbindGlobal("draftMode", this.invention.getDraftMode());
	}
}
