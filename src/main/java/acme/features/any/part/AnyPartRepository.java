
package acme.features.any.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.part.Part;

@Repository
public interface AnyPartRepository extends AbstractRepository {

	@Query("select p from Part p where p.invention.id = :id")
	Collection<Part> findAllPartByInventionId(int id);

	@Query("select p from Part p where p.id=:id")
	Part findPartById(int id);
}
