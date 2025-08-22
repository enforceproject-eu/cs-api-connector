package org.n52.project.enforce.db.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <p>
 * StatusNote DTO.
 * </p>
 *
 * @author Benjamin Pross (b.pross @52north.org)
 * @since 1.0.0
 */
@Entity
@Table(name = "status_notes")
public class StatusNote {

    @Id
    private UUID id;
    
	@Column(name = "status_note")
	private String statusNote;

	@Column(name = "status")
	private String status;

	@Column(name = "updated_datetime")
	private OffsetDateTime updatedDatetime;

	@Column(name = "status_descriptive_name")
	private String statusDescriptiveName;

	public StatusNote() {
		id = UUID.randomUUID();
	}
	
	public StatusNote(String statusNote, String status, OffsetDateTime updatedDatetime, String statusDescriptiveName) {
		this();
		this.statusNote = statusNote;
		this.status = status;
		this.updatedDatetime = updatedDatetime;
		this.statusDescriptiveName = statusDescriptiveName;
	}
	
	public String getStatusNote() {
	    return statusNote;
	}

	public void setStatusNote(String statusNote) {
	    this.statusNote = statusNote;
	}

	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}

	public OffsetDateTime getUpdatedDatetime() {
	    return updatedDatetime;
	}

	public void setUpdatedDatetime(OffsetDateTime updatedDatetime) {
	    this.updatedDatetime = updatedDatetime;
	}

	public String getStatusDescriptiveName() {
	    return statusDescriptiveName;
	}

	public void setStatusDescriptiveName(String statusDescriptiveName) {
	    this.statusDescriptiveName = statusDescriptiveName;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("statusNote: " + statusNote);
		sb.append("status: " + status);
		sb.append("updatedDatetime: " + updatedDatetime);
		sb.append("statusDescriptiveName: " + statusDescriptiveName);
		return sb.toString();
	}
	
}
