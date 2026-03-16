
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select s from Sponsorship s where s.sponsor.id = :id")
	Collection<Sponsorship> findAllSponsorshipBySponsor(int id);

	@Query("select count(d) from Donation d where d.sponsorship.id = :id")
	Integer getNumDonationsBySponsorshipId(int id);
}
