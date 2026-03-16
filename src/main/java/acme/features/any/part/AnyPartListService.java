
package acme.features.any.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.features.any.invention.AnyInventionRepository;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	//Internal state
	@Autowired
	private AnyPartRepository		repository;
	@Autowired
	private AnyInventionRepository	inventionRepository;
	private Collection<Part>		part;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("inventionId", int.class);
		this.part = this.repository.findAllPartByInventionId(id);
	}

	@Override
	public void authorise() {
		boolean status = false;
		int id = super.getRequest().getData("inventionId", int.class);
		Invention inv = this.inventionRepository.findInventionById(id);
		if (inv != null)
			status = !inv.getDraftMode();
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.part, "name", "cost");
	}
}
