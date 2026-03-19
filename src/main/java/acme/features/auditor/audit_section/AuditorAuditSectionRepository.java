
package acme.features.auditor.audit_section;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_reports.AuditReport;
import acme.entities.audit_reports.AuditSection;

@Repository
public interface AuditorAuditSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.auditReport.id = :auditReportId")
	Collection<AuditSection> findAuditSections(int auditReportId);

	@Query("select ar from AuditReport ar where ar.id = :auditReportId")
	AuditReport findAuditReportById(int auditReportId);

	@Query("select s from AuditSection s where s.id = :id")
	AuditSection findAuditSectionById(int id);

}
