
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.tactic.Tactic;

@Repository
public interface FundraiserTacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.strategy.id = :id")
	Collection<Tactic> findAllTacticByStrategyId(int id);

	@Query("select t from Tactic t where t.id=:id")
	Tactic findTacticById(int id);
}
