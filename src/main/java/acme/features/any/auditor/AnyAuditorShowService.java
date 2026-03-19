
package acme.features.any.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Auditor;

@Service
public class AnyAuditorShowService extends AbstractService<Any, Auditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditorRepository	repository;

	private Auditor					auditor;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditor = this.repository.findAuditorById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;

		id = super.getRequest().getData("id", int.class);
		status = this.auditor != null && this.repository.findPublishedAuditReportsCountByAuditorId(id) > 0;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditor, "identity.name", "identity.surname", "identity.email", "firm", "highlights", "solicitor");
	}
}
