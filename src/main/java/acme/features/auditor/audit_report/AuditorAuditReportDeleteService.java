
package acme.features.auditor.audit_report;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit_reports.AuditReport;
import acme.entities.audit_reports.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportDeleteService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && this.auditReport.getAuditor().isPrincipal() && this.auditReport.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		Collection<AuditSection> auditSections;

		auditSections = this.repository.findSectionsByAuditReportId(this.auditReport.getId());
		this.repository.deleteAll(auditSections);
		this.repository.delete(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours", "auditor");
	}
}
