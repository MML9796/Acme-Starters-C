
package acme.features.auditor.audit_section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.SectionKind;
import acme.entities.audit_reports.AuditReport;
import acme.entities.audit_reports.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionCreateService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		AuditReport auditReport;
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		auditReport = this.repository.findAuditReportById(auditReportId);

		this.auditSection = super.newObject(AuditSection.class);
		this.auditSection.setAuditReport(auditReport);
	}

	@Override
	public void authorise() {
		boolean status;
		boolean fieldsNotNull;

		fieldsNotNull = this.auditSection != null && this.auditSection.getAuditReport() != null;
		status = fieldsNotNull && this.auditSection.getAuditReport().getAuditor().isPrincipal() && this.auditSection.getAuditReport().getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
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
