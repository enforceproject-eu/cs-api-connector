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
     * union.
     * </p>
     * https://conterrade.atlassian.net/browse/ADESZUERSG-1967
     * 
     * @param id
     *            a {@link java.util.UUID} object
     * @param namespaceId
     *            a {@link java.util.UUID} the namespaceId
     * @return a {@link org.locationtech.jts.geom.Geometry} object
     */
    @Query("select st_tabletogeojson()")
    String getGeoJson();

    /**
     * <p>
     * union.
     * </p>
     * https://conterrade.atlassian.net/browse/ADESZUERSG-1967
     * 
     * @param id
     *            a {@link java.util.UUID} object
     * @param namespaceId
     *            a {@link java.util.UUID} the namespaceId
     * @return a {@link org.locationtech.jts.geom.Geometry} object
     */
    @Query("select st_tabletogeojsonwithlimit(cast(:limit as int))")
    String getGeoJsonWithLimit(@Param("limit") Integer limit);

}
