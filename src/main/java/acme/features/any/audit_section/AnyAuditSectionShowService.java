
package acme.features.any.audit_section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.SectionKind;
import acme.entities.audit_reports.AuditReport;
import acme.entities.audit_reports.AuditSection;

@Service
public class AnyAuditSectionShowService extends AbstractService<Any, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditSection				auditSection;

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
		AuditReport parent;
		int auditReportId;

		if (this.auditSection != null) {
			auditReportId = this.auditSection.getAuditReport().getId();
			parent = this.repository.findAuditReportById(auditReportId);
			status = parent != null && !parent.getDraftMode();
		} else
			status = false;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("kinds", choices);
	}

}
