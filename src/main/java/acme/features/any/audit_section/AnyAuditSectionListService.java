
package acme.features.any.audit_section;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit_reports.AuditReport;
import acme.entities.audit_reports.AuditSection;

@Service
public class AnyAuditSectionListService extends AbstractService<Any, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditSectionRepository	repository;

	private Collection<AuditSection>	auditSections;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditSections = this.repository.findAuditSections(auditReportId);
	}

	@Override
	public void authorise() {
		boolean status;
		AuditReport parent;
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		parent = this.repository.findAuditReportById(auditReportId);
		status = parent != null && !parent.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");
	}

}
