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

import org.openecomp.dcae.inventory.dbthings.models.DCAEServiceTypeObject;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

/**
 * Created by mhwang on 5/19/16.
 */
public abstract class DCAEServiceTypeTransactionDAO {

    @CreateSqlObject
    abstract DCAEServiceTypesDAO getServiceTypesDAO();

    public void upsert(DCAEServiceTypeObject newerVersion) {
        // Order of operation is important here!
        this.getServiceTypesDAO().deactivateExisting(newerVersion.getTypeName());
        this.getServiceTypesDAO().insertNewerVersion(newerVersion);
    }

}
