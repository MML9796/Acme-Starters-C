
package acme.features.auditor.audit_report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit_reports.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportPublishService extends AbstractService<Auditor, AuditReport> {

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
		super.validateObject(this.auditReport);
		{

			boolean atLeastOneAuditSection;
			int existingAuditSections;

			existingAuditSections = this.repository.findAuditSectionsSizeById(this.auditReport.getId());
			atLeastOneAuditSection = existingAuditSections >= 1;

			super.state(atLeastOneAuditSection, "*", "acme.validation.audit-report.no-audit-sections.message");

		}
	}

	@Override
	public void execute() {
		this.auditReport.setDraftMode(false);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours", "auditor");
	}
}
