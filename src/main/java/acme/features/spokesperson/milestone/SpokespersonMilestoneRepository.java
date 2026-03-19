
package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.milestones.Milestone;

@Repository
public interface SpokespersonMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :id")
	Collection<Milestone> findAllMilestoneByCampaignId(int id);

	@Query("select count(m) from Milestone m where m.campaign.id = :id")
	Double countAllMilestoneByCampaignId(int id);

	@Query("select m from Milestone m where m.id=:id")
	Milestone findMilestoneById(int id);

}
