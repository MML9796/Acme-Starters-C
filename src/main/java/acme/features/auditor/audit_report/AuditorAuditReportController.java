
package acme.features.auditor.audit_report;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audit_reports.AuditReport;
import acme.realms.Auditor;

@Controller
public class AuditorAuditReportController extends AbstractController<Auditor, AuditReport> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuditorAuditReportListService.class);
		super.addBasicCommand("show", AuditorAuditReportShowService.class);
		super.addBasicCommand("create", AuditorAuditReportCreateService.class);
		super.addBasicCommand("update", AuditorAuditReportUpdateService.class);
		super.addBasicCommand("delete", AuditorAuditReportDeleteService.class);

		super.addCustomCommand("publish", "update", AuditorAuditReportPublishService.class);
	}

}
