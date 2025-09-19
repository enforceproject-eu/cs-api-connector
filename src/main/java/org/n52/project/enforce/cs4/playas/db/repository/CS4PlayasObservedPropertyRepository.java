package org.n52.project.enforce.cs4.playas.db.repository;

import java.util.Optional;

import org.n52.project.enforce.cs4.playas.db.model.CS4PlayasObservedProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * DataReferenceRepository interface.
 * </p>
 *
 * @author Benjamin Pross (b.pross@52north.org)
 * @since 1.0.0
 */
@Repository
public interface CS4PlayasObservedPropertyRepository extends JpaRepository<CS4PlayasObservedProperty, Integer> {

    /**
     * <p>
     * findById.
     * </p>
     *
     * @param id
     *            an Integer
     * @return a
     *         {@link CS4PlayasObservedProperty}
     *         object
     */
    Optional<CS4PlayasObservedProperty> findById(Integer id);
}
