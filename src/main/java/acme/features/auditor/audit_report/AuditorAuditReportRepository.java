
package acme.features.auditor.audit_report;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_reports.AuditReport;
import acme.entities.audit_reports.AuditSection;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("select ar from AuditReport ar where ar.auditor.id = :auditorId")
	Collection<AuditReport> findOwnedAuditReports(int auditorId);

	@Query("select ar from AuditReport ar where ar.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select ar from AuditReport ar where ar.ticker = :ticker")
	AuditReport findAuditReportByTicker(String ticker);

	@Query("select count(s) from AuditSection s where s.auditReport.id = :id")
	int findAuditSectionsSizeById(int id);

	@Query("select s from AuditSection s where s.auditReport.id = :auditReportId")
	Collection<AuditSection> findSectionsByAuditReportId(int auditReportId);

}
