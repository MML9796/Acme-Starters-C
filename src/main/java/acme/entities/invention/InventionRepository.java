
package acme.entities.invention;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface InventionRepository extends AbstractRepository {

	@Query("SELECT SUM(p.cost.amount) FROM Part p WHERE p.invention.id = :inventionId")
	Double getSumCostsPartsByInvention(int inventionId);

	@Query("SELECT COUNT(p) FROM Part p WHERE p.invention.id = :inventionId")
	Integer findPartsByInventionId(int inventionId);

	@Query("select inv from Invention inv where inv.ticker = :ticker")
	Invention findInventionByTicker(String ticker);

}
