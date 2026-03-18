
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.features.inventor.part.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorInventionDeleteService extends AbstractService<Inventor, Invention> {

	//Internal state
	@Autowired
	private InventorInventionRepository	repository;
	@Autowired
	private InventorPartRepository		partRepository;
	private Invention					invention;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int inventorId;
		inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = this.invention != null && this.invention.getInventor().getId() == inventorId && this.invention.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
	}

	@Override
	public void execute() {
		Collection<Part> parts;
		int id;
		id = super.getRequest().getData("id", int.class);
		parts = this.partRepository.findAllPartByInventionId(id);
		parts.stream().forEach(m -> this.partRepository.delete(m));
		this.repository.delete(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}

}
