
package acme.features.sponsor.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.donation.Donation;

@Repository
public interface SponsorDonationRepository extends AbstractRepository {

	@Query("select d from Donation d where d.sponsorship.id = :id")
	Collection<Donation> findAllDonationBySponsorshipId(int id);

	@Query("select d from Donation d where d.id=:id")
	Donation findDonationById(int id);

}
