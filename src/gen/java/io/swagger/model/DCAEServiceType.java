package io.swagger.model;

/*
 * ============LICENSE_START==========================================
 * ===================================================================
 * Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
 * ===================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END============================================
 *
 * ECOMP and OpenECOMP are trademarks 
 * and service marks of AT&T Intellectual Property.
 *
 */

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-04-18T20:16:22.119Z")
public class DCAEServiceType   {
  
  private String typeName = null;
  private Link selfLink = null;
  private Date created = null;
  private String owner = null;
  private List<String> vnfTypes = new ArrayList<String>();
    private List<String> serviceIds = null;
    private List<String> serviceLocations = null;
  private String blueprintTemplate = null;

  /**
   **/
  public DCAEServiceType typeName(String typeName) {
    this.typeName = typeName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Unique name for this DCAE service type")
  @JsonProperty("typeName")
  public String getTypeName() {
    return typeName;
  }
  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  /**
   * Link.title is typeName
   **/
  public DCAEServiceType selfLink(Link selfLink) {
    this.selfLink = selfLink;
    return this;
  }

  
  @ApiModelProperty(value = "Link.title is typeName")
  @JsonProperty("selfLink")
  public Link getSelfLink() {
    return selfLink;
  }
  public void setSelfLink(Link selfLink) {
    this.selfLink = selfLink;
  }

  /**
   **/
  public DCAEServiceType created(Date created) {
    this.created = created;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Created timestamp for this DCAE service type in epoch time")
  @JsonProperty("created")
  public Date getCreated() {
    return created;
  }
  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   **/
  public DCAEServiceType owner(String owner) {
    this.owner = owner;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name of the owner of this DCAE service type")
  @JsonProperty("owner")
  public String getOwner() {
    return owner;
  }
  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   **/
  public DCAEServiceType vnfTypes(List<String> vnfTypes) {
    this.vnfTypes = vnfTypes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "List of VNF types associated with this DCAE service type")
  @JsonProperty("vnfTypes")
  public List<String> getVnfTypes() {
    return vnfTypes;
  }
  public void setVnfTypes(List<String> vnfTypes) {
    this.vnfTypes = vnfTypes;
  }

    @ApiModelProperty(required = false, value = "List of service ids that are associated with this DCAE service type")
    @JsonProperty("serviceIds")
    public List<String> getServiceIds() {
        return serviceIds;
    }
    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
    }

    @ApiModelProperty(required = false, value = "List of service locations that are associated with this DCAE service type")
    @JsonProperty("serviceLocations")
    public List<String> getServiceLocations() {
        return serviceLocations;
    }
    public void setServiceLocations(List<String> serviceLocations) {
        this.serviceLocations = serviceLocations;
    }

    /**
   * String representation of a Cloudify blueprint with unbound variables
   **/
  public DCAEServiceType blueprintTemplate(String blueprintTemplate) {
    this.blueprintTemplate = blueprintTemplate;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "String representation of a Cloudify blueprint with unbound variables")
  @JsonProperty("blueprintTemplate")
  public String getBlueprintTemplate() {
    return blueprintTemplate;
  }
  public void setBlueprintTemplate(String blueprintTemplate) {
    this.blueprintTemplate = blueprintTemplate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DCAEServiceType dCAEServiceType = (DCAEServiceType) o;
    return Objects.equals(typeName, dCAEServiceType.typeName) &&
        Objects.equals(selfLink, dCAEServiceType.selfLink) &&
        Objects.equals(created, dCAEServiceType.created) &&
        Objects.equals(owner, dCAEServiceType.owner) &&
        Objects.equals(vnfTypes, dCAEServiceType.vnfTypes) &&
        Objects.equals(blueprintTemplate, dCAEServiceType.blueprintTemplate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeName, selfLink, created, owner, vnfTypes, blueprintTemplate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DCAEServiceType {\n");
    
    sb.append("    typeName: ").append(toIndentedString(typeName)).append("\n");
    sb.append("    selfLink: ").append(toIndentedString(selfLink)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    vnfTypes: ").append(toIndentedString(vnfTypes)).append("\n");
    sb.append("    blueprintTemplate: ").append(toIndentedString(blueprintTemplate)).append("\n");
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

