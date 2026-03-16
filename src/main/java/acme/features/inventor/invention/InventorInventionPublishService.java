
package acme.features.inventor.invention;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	//Internal state
	@Autowired
	private InventorInventionRepository	repository;
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
		int inventorId, inventionId;
		Invention inv;

		inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		inventionId = super.getRequest().getData("id", int.class);

		inv = this.repository.findInventionById(inventionId);

		status = inv != null && inv.getInventor().getId() == inventorId && inv.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		Integer numParts;
		Date publishMoment;

		numParts = this.repository.getNumPartsByInventionId(this.invention.getId());
		publishMoment = MomentHelper.getCurrentMoment();

		super.state(numParts > 0, "*", "acme.validation.invention.no-parts.message");
		super.state(publishMoment.before(this.invention.getStartMoment()), "startMoment", "acme.validation.invention.publish-after-start.message");
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}

}
