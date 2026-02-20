
package acme.entities.campaign;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface CampaignRepository extends AbstractRepository {

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = ?1")
	Double sumEffortByCampaignId(int campaignId);

	@Query("select count(m) from Milestone m where m.campaign.id = ?1")
	Integer totalMilestoneByCamapaignId(int campaignId);
}
