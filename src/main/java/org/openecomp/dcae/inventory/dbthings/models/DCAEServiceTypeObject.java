package org.openecomp.dcae.inventory.dbthings.models;

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

import org.joda.time.DateTime;

import java.util.List;

/**
 * POJO representation of a record in dcae_service_types table.
 *
 * Created by mhwang on 5/3/16.
 */
public class DCAEServiceTypeObject {

    private String typeName = null;
    private String owner = null;
    private List<String> vnfTypes = null;
    private List<String> serviceIds = null;
    private List<String> serviceLocations = null;
    private String blueprintTemplate = null;
    private DateTime created = null;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getVnfTypes() {
        return vnfTypes;
    }

    public void setVnfTypes(List<String> vnfTypes) {
        this.vnfTypes = vnfTypes;
    }

    public List<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public List<String> getServiceLocations() {
        return serviceLocations;
    }

    public void setServiceLocations(List<String> serviceLocations) {
        this.serviceLocations = serviceLocations;
    }

    public String getBlueprintTemplate() {
        return blueprintTemplate;
    }

    public void setBlueprintTemplate(String blueprintTemplate) {
        this.blueprintTemplate = blueprintTemplate;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

}
