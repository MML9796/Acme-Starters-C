
package acme.features.any.auditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Auditor;

@Repository
public interface AnyAuditorRepository extends AbstractRepository {

	@Query("select count(ar) from AuditReport ar where ar.draftMode = false and ar.auditor.id = :id")
	int findPublishedAuditReportsCountByAuditorId(int id);

	@Query("select a from Auditor a where a.id = :id")
	Auditor findAuditorById(int id);

}
