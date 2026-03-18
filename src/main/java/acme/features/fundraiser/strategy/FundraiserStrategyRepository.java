
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategy.Strategy;

@Repository
public interface FundraiserStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

	@Query("select s from Strategy s where s.fundraiser.id = :id")
	Collection<Strategy> findAllStrategyByFundraiserId(int id);

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :id")
	Double sumPercentageByStrategyId(int id);
}
