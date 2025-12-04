package org.n52.project.enforce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * FeatureCollection
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T11:20:45.740105900+01:00[Europe/Berlin]", comments = "Generator version: 7.13.0")
public class FeatureCollection implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    FEATURE_COLLECTION("FeatureCollection");

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

  @Valid
  private List<@Valid FeatureGeoJSON> features = new ArrayList<>();

  public FeatureCollection() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FeatureCollection(TypeEnum type, List<@Valid FeatureGeoJSON> features) {
    this.type = type;
    this.features = features;
  }

  public FeatureCollection type(TypeEnum type) {
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

  public FeatureCollection features(List<@Valid FeatureGeoJSON> features) {
    this.features = features;
    return this;
  }

  public FeatureCollection addFeaturesItem(FeatureGeoJSON featuresItem) {
    if (this.features == null) {
      this.features = new ArrayList<>();
    }
    this.features.add(featuresItem);
    return this;
  }

  /**
   * Get features
   * @return features
   */
  @NotNull @Valid 
  @Schema(name = "features", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("features")
  public List<@Valid FeatureGeoJSON> getFeatures() {
    return features;
  }

  public void setFeatures(List<@Valid FeatureGeoJSON> features) {
    this.features = features;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FeatureCollection featureCollection = (FeatureCollection) o;
    return Objects.equals(this.type, featureCollection.type) &&
        Objects.equals(this.features, featureCollection.features);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, features);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FeatureCollection {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    features: ").append(toIndentedString(features)).append("\n");
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

