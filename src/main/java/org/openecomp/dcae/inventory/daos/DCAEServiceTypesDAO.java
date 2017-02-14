package org.openecomp.dcae.inventory.daos;

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

import org.openecomp.dcae.inventory.dbthings.mappers.DCAEServiceTypeObjectMapper;
import org.openecomp.dcae.inventory.dbthings.models.DCAEServiceTypeObject;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * DCAE service type records are treated immutable.  Newer versions are added with the flag "is_active" set to TRUE.
 * The responsibility of managing the active version on the caller of this DAO.
 *
 * Currently the "is_active" flag is hidden to the application except in this DAO.
 *
 * Created by mhwang on 4/19/16.
 */
public interface DCAEServiceTypesDAO extends InventoryDAO {

    @SqlQuery("select exists (select * from information_schema.tables where table_name = \'dcae_service_types\')")
    Boolean checkIfTableExists();

    /**
     * Note that service_ids and service_locations are nullable fields. This might not be the right decision but because
     * the resource model allows for nulls, thought it should consistent.
     */
    @SqlUpdate("create table dcae_service_types (type_name varchar not null, owner varchar not null," +
            "vnf_types varchar[] not null, service_ids varchar[], service_locations varchar[], blueprint_template text not null, " +
            "created timestamp not null, is_active boolean not null, constraint pk_type_created primary key (type_name, created))")
    void createTable();

    @SqlUpdate("insert into dcae_service_types(type_name, owner, vnf_types, service_ids, service_locations, " +
            "blueprint_template, created, is_active) values (:typeName, :owner, :vnfTypes, :serviceIds, :serviceLocations, " +
            ":blueprintTemplate, :created, TRUE)")
    void insertNewerVersion(@BindBean DCAEServiceTypeObject serviceObject);

    @SqlUpdate("update dcae_service_types set is_active = FALSE where type_name = :typeName and is_active = TRUE")
    void deactivateExisting(@Bind("typeName") String typeName);

    @Mapper(DCAEServiceTypeObjectMapper.class)
    @SqlQuery("select * from dcae_service_types where is_active = TRUE and type_name = :it")
    DCAEServiceTypeObject getByTypeName(@Bind String typeName);

}
