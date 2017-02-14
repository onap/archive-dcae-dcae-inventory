package org.openecomp.dcae.inventory.dbthings.mappers;

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

import org.openecomp.dcae.inventory.dbthings.models.DCAEServiceTypeObject;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by mhwang on 5/3/16.
 */
public class DCAEServiceTypeObjectMapper implements ResultSetMapper<DCAEServiceTypeObject> {

    @Override
    public DCAEServiceTypeObject map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        DCAEServiceTypeObject object = new DCAEServiceTypeObject();
        object.setTypeName(resultSet.getString("type_name"));
        object.setOwner(resultSet.getString("owner"));
        String[] vnfTypes = (String[]) resultSet.getArray("vnf_types").getArray();
        object.setVnfTypes(Arrays.asList(vnfTypes));

        Array serviceIdsArray = resultSet.getArray("service_ids");

        if (serviceIdsArray != null) {
            String[] serviceIds = (String[]) serviceIdsArray.getArray();
            object.setServiceIds(Arrays.asList(serviceIds));
        }

        Array serviceLocationsArray = resultSet.getArray("service_locations");

        if (serviceLocationsArray != null) {
            String[] serviceLocations = (String[]) serviceLocationsArray.getArray();
            object.setServiceLocations(Arrays.asList(serviceLocations));
        }

        object.setBlueprintTemplate(resultSet.getString("blueprint_template"));
        object.setCreated(new DateTime(resultSet.getTimestamp("created")));
        return object;
    }

}
