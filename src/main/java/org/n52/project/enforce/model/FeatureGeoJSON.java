package org.n52.project.enforce.model;

import java.io.Serializable;
import java.util.Objects;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * FeatureGeoJSON
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T11:20:45.740105900+01:00[Europe/Berlin]", comments = "Generator version: 7.13.0")
public class FeatureGeoJSON implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    FEATURE("Feature");

    private final String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private GeometryGeoJSON geometry;

  private JsonNullable<Object> properties = JsonNullable.<Object>undefined();

  private @Nullable FeatureGeoJSONId id;

  public FeatureGeoJSON() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FeatureGeoJSON(TypeEnum type, GeometryGeoJSON geometry, Object properties) {
    this.type = type;
    this.geometry = geometry;
    this.properties = JsonNullable.of(properties);
  }

  public FeatureGeoJSON type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public FeatureGeoJSON geometry(GeometryGeoJSON geometry) {
    this.geometry = geometry;
    return this;
  }

  /**
   * Get geometry
   * @return geometry
   */
  @NotNull @Valid 
  @Schema(name = "geometry", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("geometry")
  public GeometryGeoJSON getGeometry() {
    return geometry;
  }

  public void setGeometry(GeometryGeoJSON geometry) {
    this.geometry = geometry;
  }

  public FeatureGeoJSON properties(Object properties) {
    this.properties = JsonNullable.of(properties);
    return this;
  }

  /**
   * Get properties
   * @return properties
   */
  @NotNull 
  @Schema(name = "properties", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("properties")
  public JsonNullable<Object> getProperties() {
    return properties;
  }

  public void setProperties(JsonNullable<Object> properties) {
    this.properties = properties;
  }

  public FeatureGeoJSON id(FeatureGeoJSONId id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public FeatureGeoJSONId getId() {
    return id;
  }

  public void setId(FeatureGeoJSONId id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FeatureGeoJSON featureGeoJSON = (FeatureGeoJSON) o;
    return Objects.equals(this.type, featureGeoJSON.type) &&
        Objects.equals(this.geometry, featureGeoJSON.geometry) &&
        Objects.equals(this.properties, featureGeoJSON.properties) &&
        Objects.equals(this.id, featureGeoJSON.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, geometry, properties, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FeatureGeoJSON {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    geometry: ").append(toIndentedString(geometry)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

