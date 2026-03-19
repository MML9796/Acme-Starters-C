
package acme.features.any.audit_report;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_reports.AuditReport;

@Repository
public interface AnyAuditReportRepository extends AbstractRepository {

	@Query("select ar from AuditReport ar where ar.draftMode = false")
	Collection<AuditReport> findPublishedAuditReports();

	@Query("select ar from AuditReport ar where ar.id = :id")
	AuditReport findAuditReportById(int id);

}
