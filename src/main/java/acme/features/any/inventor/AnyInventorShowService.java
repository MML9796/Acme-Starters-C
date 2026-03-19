
package acme.features.any.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Inventor;

@Service
public class AnyInventorShowService extends AbstractService<Any, Inventor> {

	//Internal state
	@Autowired
	private AnyInventorRepository	repository;
	private Inventor				inventor;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.inventor = this.repository.findInventorById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;
		if (this.inventor != null)
			status = true;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.inventor, "userAccount.username", "bio", "keyWords", "licensed");
	}
}
