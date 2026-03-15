
package acme.entities.audit_reports;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select sum(s.hours) from AuditSection s where s.auditReport.id = :id")
	Integer computeHours(int id);

	@Query("select ar from AuditReport ar where ar.ticker = :ticker")
	AuditReport findAuditReportByTicker(String ticker);

	@Query("select count(s) from AuditSection s where s.auditReport.id = :id")
	int findAuditSectionsSizeById(int id);

}
