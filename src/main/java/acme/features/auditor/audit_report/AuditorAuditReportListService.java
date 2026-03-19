
package acme.features.auditor.audit_report;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit_reports.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportListService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private Collection<AuditReport>			auditReports;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.auditReports = this.repository.findOwnedAuditReports(auditorId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = true;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours");
	}

}
