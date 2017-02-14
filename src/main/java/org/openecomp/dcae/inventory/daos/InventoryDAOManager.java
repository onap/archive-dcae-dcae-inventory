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

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Reluctantly made this into a singleton in order to have access to the DAOs in the request handling code. Didn't
 * want to change the interface on the handlers because they are generated by Swagger and I wanted to flexibility
 * to swap in changes easily. This meant sacrificing dependency injection which is preferred.
 *
 * Created by mhwang on 4/19/16.
 */
public final class InventoryDAOManager {

    private static InventoryDAOManager instance;

    public static InventoryDAOManager getInstance() {
        if (instance == null) {
            instance = new InventoryDAOManager();
        }

        return instance;
    }

    private final static Logger LOG = LoggerFactory.getLogger(InventoryDAOManager.class);
    // WATCH! Table creation order matters where mapping tables refer to other tables for foreign keys.
    private final static List<Class> DAO_CLASSES = Arrays.asList(DCAEServiceTypesDAO.class, DCAEServicesDAO.class,
            DCAEServiceComponentsDAO.class, DCAEServicesComponentsMapsDAO.class);

    private DBI jdbi;

    private InventoryDAOManager() {
    }

    public void init(DBI jdbi) {
        this.jdbi = jdbi;

        for (Class<? extends InventoryDAO> daoClass : DAO_CLASSES) {
            final InventoryDAO dao = jdbi.onDemand(daoClass);

            if (dao.checkIfTableExists()) {
                LOG.info(String.format("Sql table exists: %s", daoClass.getSimpleName()));
            } else {
                dao.createTable();
                LOG.info(String.format("Sql table created: %s", daoClass.getSimpleName()));
            }
        }
    }

    private InventoryDAO getDAO(Class<? extends InventoryDAO> klass) {
        if (jdbi == null) {
            throw new RuntimeException("InventoryDAOManager has not been initialized!");
        }

        // Using this approach to constructing the DAO, the client is not responsible for closing the handle.
        // http://jdbi.org/sql_object_overview/
        // > In this case we do not need to (and in fact shouldn’t) ever take action to close the handle the sql object uses.
        return jdbi.onDemand(klass);
    }

    public DCAEServicesDAO getDCAEServicesDAO() {
        return (DCAEServicesDAO) this.getDAO(DCAEServicesDAO.class);
    }

    public DCAEServiceComponentsDAO getDCAEServiceComponentsDAO() {
        return (DCAEServiceComponentsDAO) this.getDAO(DCAEServiceComponentsDAO.class);
    }

    public DCAEServicesComponentsMapsDAO getDCAEServicesComponentsDAO() {
        return (DCAEServicesComponentsMapsDAO) this.getDAO(DCAEServicesComponentsMapsDAO.class);
    }

    public DCAEServiceTransactionDAO getDCAEServiceTransactionDAO() {
        return jdbi.onDemand(DCAEServiceTransactionDAO.class);
    }

    public DCAEServiceTypeTransactionDAO getDCAEServiceTypeTransactionDAO() {
        return jdbi.onDemand(DCAEServiceTypeTransactionDAO.class);
    }

    public DCAEServiceTypesDAO getDCAEServiceTypesDAO() {
        return (DCAEServiceTypesDAO) this.getDAO(DCAEServiceTypesDAO.class);
    }

    /**
     * Must close the handle that is returned here. It is AutoCloseable so just use it as a try-with-resource.
     *
     * @return
     */
    public Handle getHandle() {
        return this.jdbi.open();
    }

}
