package org.n52.project.enforce.db.repository;

import java.util.UUID;

import org.n52.project.enforce.db.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * Data repository.
 * </p>
 *
 * @author Benjamin Pross (spross (at)muenster.de)
 * @since 1.0.0
 */
public interface DataRepository extends JpaRepository<Data, UUID> {

    /**
     * <p>
     * getGeoJson.
     * </p>
     * @return a {@link String} object
     */
    @Query("select st_tabletogeojson()")
    String getGeoJson();

    /**
     * <p>
     * getGeoJsonWithLimit.
     * </p>
     * @return a {@link String} object
     */
    @Query("select st_tabletogeojsonwithlimit(cast(:limit as int))")
    String getGeoJsonWithLimit(@Param("limit") Integer limit);

}
