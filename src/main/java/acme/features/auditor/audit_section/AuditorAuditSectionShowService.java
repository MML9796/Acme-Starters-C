
package acme.features.auditor.audit_section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.SectionKind;
import acme.entities.audit_reports.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionShowService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditSection = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		boolean fieldsNotNull;

		fieldsNotNull = this.auditSection != null && this.auditSection.getAuditReport() != null;
		status = fieldsNotNull && this.auditSection.getAuditReport().getAuditor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind", "auditReport");
		tuple.put("kinds", choices);
	}

}
