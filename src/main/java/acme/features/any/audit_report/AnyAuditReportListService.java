
package acme.features.any.audit_report;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit_reports.AuditReport;

@Service
public class AnyAuditReportListService extends AbstractService<Any, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditReportRepository	repository;

	private Collection<AuditReport>		auditReports;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.auditReports = this.repository.findPublishedAuditReports();
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
