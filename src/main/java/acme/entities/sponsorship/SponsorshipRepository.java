
package acme.entities.sponsorship;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.donation.Donation;

@Repository
public interface SponsorshipRepository extends AbstractRepository {

	@Query("SELECT SUM(d.money.amount) from Donation d where d.sponsorship.id = :id")
	Double getSumTotalMoneyBySponsorship(@Param("id") int sponsorshipId);

	@Query("SELECT d FROM Donation d WHERE d.sponsorship.id = :sponsorshipId")
	List<Donation> findDonationBySponsorshipId(int sponsorshipId);
}
