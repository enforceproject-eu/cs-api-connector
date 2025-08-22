package org.n52.project.enforce.db.model;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "data")
public class Data {

    @Id
    private UUID id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "species_name")
	private String speciesName;

	@Column(name = "location",
			columnDefinition = "geometry(Point,4326)")
	private Point location;

	@Column(name = "observed_datetime")
	private OffsetDateTime observedDatetime;

	@Column(name = "updated_datetime")
	private OffsetDateTime updatedDatetime;

	@Column(name = "media_url")
	private URL mediaUrl;

    @ElementCollection
    @JoinTable(
            name = "data_status_notes",
            joinColumns = @JoinColumn(
                    name = "id"))
    @Column(
            name = "status_notes")
    private Set<StatusNote> statusNotes = new HashSet<StatusNote>();
	
	public Data() {
		this.id = UUID.randomUUID();
	}
	
	public Data(UUID id) {
		this.id = id;
	}

	public Data(UUID id, Integer userId, String speciesName, Point location, OffsetDateTime observedDatetime, OffsetDateTime updatedDatetime, 
			URL mediaUrl) {
		this(id);
		this.userId = userId;
		this.speciesName = speciesName;
		this.location = location;
		this.observedDatetime = observedDatetime;
		this.updatedDatetime = updatedDatetime;
		this.mediaUrl = mediaUrl;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public OffsetDateTime getObservedDatetime() {
		return observedDatetime;
	}

	public void setObservedDatetime(OffsetDateTime observedDatetime) {
		this.observedDatetime = observedDatetime;
	}

	public OffsetDateTime getUpdatedDatetime() {
		return updatedDatetime;
	}

	public void setUpdatedDatetime(OffsetDateTime updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}

	public URL getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(URL mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public Set<StatusNote> getStatusNotes() {
		return statusNotes;
	}

	public void setStatusNotes(Set<StatusNote> statusNotes) {
		this.statusNotes = statusNotes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("userId: " + userId);
		sb.append("speciesName: " + speciesName);
		sb.append("location: " + location);
		sb.append("observedDatetime: " + observedDatetime);
		sb.append("updatedDatetime: " + updatedDatetime);
		sb.append("mediaUrl: " + mediaUrl);
		return sb.toString();
	}
}
