
package acme.entities.strategy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface StrategyRepository extends AbstractRepository {

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :id")
	Double sumPercentageByStrategyId(int id);

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	Integer countTacticsByStrategyId(int strategyId);

	@Query("select s from Strategy s where s.ticker = :ticker")
	Strategy findStrategyByTicker(String ticker);
}
