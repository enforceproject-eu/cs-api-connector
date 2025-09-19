package org.n52.project.enforce.cs4.playas.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <p>
 * DataReference class.
 * </p>
 *
 * @author Benjamin Pross (b.pross@52north.org)
 * @since 1.0.0
 */
@Entity
@Table(
        name = "cs4playas_observed_property")
public class CS4PlayasObservedProperty {

    @Id
    private Integer id;

    @Column(
            name = "name")
    private String name;

    /**
     * <p>
     * Constructor for DataReference.
     * </p>
     */
    public CS4PlayasObservedProperty() {

    }

    /**
     * <p>
     * Constructor for DataReference.
     * </p>
     *
     * @param datareferencePK
     *            a
     *            {@link ObservedPropertyPK.conterra.businesstools.nrtlageservice.db.model.DatareferencePK}
     *            object
     */
    public CS4PlayasObservedProperty(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * <p>
     * Getter for the field <code>id</code>.
     * </p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>
     * Setter for the field <code>id</code>.
     * </p>
     *
     * @param date
     *            a {@link java.lang.Integer} object
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>
     * Getter for the field <code>name</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Setter for the field <code>name</code>.
     * </p>
     *
     * @param name
     *            a {@link java.lang.String} object
     */
    public void setName(String name) {
        this.name = name;
    }
}
