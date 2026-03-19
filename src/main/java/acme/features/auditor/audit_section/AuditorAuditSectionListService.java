
package acme.features.auditor.audit_section;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit_reports.AuditReport;
import acme.entities.audit_reports.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionListService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private Collection<AuditSection>		auditSections;
	private AuditReport						parent;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditSections = this.repository.findAuditSections(auditReportId);
		this.parent = this.repository.findAuditReportById(auditReportId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.parent != null && this.parent.getAuditor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		int auditReportId;
		boolean showCreate;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		showCreate = this.parent.getDraftMode();

		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");
		super.unbindGlobal("auditReportId", auditReportId);
		super.unbindGlobal("showCreate", showCreate);
	}

}
