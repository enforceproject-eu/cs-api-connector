package org.n52.project.enforce.cs4.playas.db.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * <p>
 * Data DTO.
 * </p>
 *
 * @author Benjamin Pross (b.pross @52north.org)
 * @since 1.0.0
 */
@Entity
@Table(
        name = "cs4playas_data")
public class CS4PlayasData {

    @Id
    private Integer id;

    @Column(
            name = "location_name")
    private String locationName;

    @Column(
            name = "entity")
    private String entity;

    @Column(
            name = "country")
    private String country;

    @Column(
            name = "community")
    private String community;

    @Column(
            name = "province")
    private String province;

    @Column(
            name = "municipality")
    private String municipality;

    @Column(
            name = "collection_type")
    private String collectionType;

    @Column(
            name = "project")
    private String project;

    @Column(
            name = "monitoring_length_meters")
    private Integer monitoringLengthMeters;

    @Column(
            name = "initial_coordinates",
            columnDefinition = "geometry(Point,4326)")
    private Point initialCoordinates;

    @Column(
            name = "final_coordinates",
            columnDefinition = "geometry(Point,4326)")
    private Point finalCoordinates;

    @Column(
            name = "cleaning_date")
    private Date cleaningDate;

    @Column(
            name = "total_items")
    private Integer totalItems;

    @Column(
            name = "remarks")
    private String remarks;

    
//    @JoinTable(
//            name = "cs4_playas_data_observed_property_count",
//            joinColumns = @JoinColumn(
//                    name = "id"))
//    @Column(
//            name = "observed_property_count")
//    @ElementCollection
//    @MapKeyColumn(name="observed_property_id")
//    @Column(name="count")
    @ElementCollection
    private Map<CS4PlayasObservedProperty, Integer> observedPropertyCount = new HashMap<>();

//    @ManyToMany
//    @JoinTable(
//            name = "cs4_playas_data_observed_properties",
//            joinColumns = @JoinColumn(
//                    name = "id"))
//    @Column(
//            name = "observed_properties")
//    private Set<ObservedProperty> observedProperties = new HashSet<ObservedProperty>();

    public CS4PlayasData() {

    }

    public CS4PlayasData(Integer id) {
        this.id = id;
    }

    public CS4PlayasData(Integer id, String entity, String locationName, String country, String community,
            String province, String municipality, String project, String collectionType, Integer monitoringLenghtMeters,
            Point initialCoordinates, Point finalCoordinates, Date cleaningDate, Integer totalItems, String remarks) {
        this(id);
        this.entity = entity;
        this.locationName = locationName;
        this.country = country;
        this.community = community;
        this.province = province;
        this.municipality = municipality;
        this.project = project;
        this.collectionType = collectionType;
        this.monitoringLengthMeters = monitoringLenghtMeters;
        this.initialCoordinates = initialCoordinates;
        this.finalCoordinates = finalCoordinates;
        this.cleaningDate = cleaningDate;
        this.totalItems = totalItems;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getMonitoringLengthMeters() {
        return monitoringLengthMeters;
    }

    public void setMonitoringLengthMeters(Integer monitoringLengthMeters) {
        this.monitoringLengthMeters = monitoringLengthMeters;
    }

    public Point getInitialCoordinates() {
        return initialCoordinates;
    }

    public void setInitialCoordinates(Point initialCoordinates) {
        this.initialCoordinates = initialCoordinates;
    }

    public Point getFinalCoordinates() {
        return finalCoordinates;
    }

    public void setFinalCoordinates(Point finalCoordinates) {
        this.finalCoordinates = finalCoordinates;
    }

    public Date getCleaningDate() {
        return cleaningDate;
    }

    public void setCleaningDate(Date cleaningDate) {
        this.cleaningDate = cleaningDate;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Map<CS4PlayasObservedProperty, Integer> getObservedPropertyCount() {
        return observedPropertyCount;
    }

    public void setObservedPropertyCount(Map<CS4PlayasObservedProperty, Integer> observedPropertyCount) {
        this.observedPropertyCount = observedPropertyCount;
    }

    public void putObservedPropertyCount(CS4PlayasObservedProperty observedProperty, Integer count) {
        if (observedPropertyCount == null) {
            this.observedPropertyCount = new HashMap<>();
        }
        this.observedPropertyCount.put(observedProperty, count);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id + ", ");
        sb.append("entity: " + entity + ", ");
        sb.append("locationName: " + locationName + ", ");
        sb.append("country: " + country + ", ");
        sb.append("community: " + community + ", ");
        sb.append("province: " + province + ", ");
        sb.append("municipality: " + municipality + ", ");
        sb.append("project: " + project + ", ");
        sb.append("collectionType: " + collectionType + ", ");
        sb.append("monitoringLenghtMeters: " + monitoringLengthMeters + ", ");
        sb.append("initialCoordinates: " + initialCoordinates + ", ");
        sb.append("finalCoordinates: " + finalCoordinates + ", ");
        sb.append("cleaningDate: " + cleaningDate + ", ");
        sb.append("remarks: " + remarks + ", ");
        sb.append("totalItems: " + totalItems);
        return sb.toString();
    }
}
