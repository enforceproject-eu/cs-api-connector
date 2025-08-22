package org.n52.project.enforce.db.repository;

import java.util.UUID;

import org.n52.project.enforce.db.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Data repository.
 * </p>
 *
 * @author Benjamin Pross (spross (at)muenster.de)
 * @since 1.0.0
 */
public interface DataRepository extends JpaRepository<Data, UUID> {

}
