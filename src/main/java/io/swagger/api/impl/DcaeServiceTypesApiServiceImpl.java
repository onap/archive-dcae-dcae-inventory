package io.swagger.api.impl;

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

import org.openecomp.dcae.inventory.daos.DCAEServiceTypesDAO;
import org.openecomp.dcae.inventory.daos.DCAEServicesDAO;
import org.openecomp.dcae.inventory.daos.InventoryDAOManager;
import org.openecomp.dcae.inventory.dbthings.mappers.DCAEServiceTypeObjectMapper;
import org.openecomp.dcae.inventory.dbthings.models.DCAEServiceObject;
import org.openecomp.dcae.inventory.dbthings.models.DCAEServiceTypeObject;
import io.swagger.api.*;
import io.swagger.model.*;

import io.swagger.api.NotFoundException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-04-18T20:16:22.119Z")
public class DcaeServiceTypesApiServiceImpl extends DcaeServiceTypesApiService {

    private final static Logger LOG = LoggerFactory.getLogger(DcaeServiceTypesApiServiceImpl.class);
    private static int PAGINATION_PAGE_SIZE = 25;

    private DCAEServiceType createDCAEServiceType(DCAEServiceTypeObject serviceTypeObject, UriInfo uriInfo) {
        DCAEServiceType serviceType = new DCAEServiceType();
        serviceType.setSelfLink(DcaeServiceTypesApi.buildLinkForGet(uriInfo, "self", serviceTypeObject.getTypeName()));
        serviceType.setTypeName(serviceTypeObject.getTypeName());
        serviceType.setOwner(serviceTypeObject.getOwner());
        serviceType.setVnfTypes(serviceTypeObject.getVnfTypes());
        serviceType.setServiceIds(serviceTypeObject.getServiceIds());
        serviceType.setServiceLocations(serviceTypeObject.getServiceLocations());
        serviceType.setBlueprintTemplate(serviceTypeObject.getBlueprintTemplate());
        serviceType.created(serviceTypeObject.getCreated().toDate());

        return serviceType;
    }

    @Override
    public Response dcaeServiceTypesGet(String vnfType, String serviceId, String serviceLocation, Integer offset, UriInfo uriInfo,
                                        SecurityContext securityContext)
            throws NotFoundException {
        List<DCAEServiceTypeObject> serviceTypeObjects = new ArrayList<>();

        // TODO: Make this variable also a URL parameter.
        DateTime createdCutoff = DateTime.now(DateTimeZone.UTC);

        try (Handle jdbiHandle = InventoryDAOManager.getInstance().getHandle()) {
            StringBuilder sb = new StringBuilder("select * from dcae_service_types");

            List<String> whereClauses = new ArrayList<String>();

            if (vnfType != null) {
                whereClauses.add(":vnfType = any(vnf_types)");
            }

            if (serviceId != null) {
                whereClauses.add("(:serviceId = any(service_ids) or service_ids = \'{}\' or service_ids is null)");
            }

            if (serviceLocation != null) {
                whereClauses.add("(:serviceLocation = any(service_locations) or service_locations = \'{}\' or service_locations is null)");
            }

            whereClauses.add("created < :createdCutoff");
            // We only want the active service types
            whereClauses.add("is_active = TRUE");

            if (!whereClauses.isEmpty()) {
                sb.append(" where ");
                sb.append(String.join(" and ", whereClauses));
            }

            // Sort by created timestamp - always descending.
            sb.append(" order by created desc");

            Query<DCAEServiceTypeObject> query = jdbiHandle.createQuery(sb.toString()).map(new DCAEServiceTypeObjectMapper());

            if (vnfType != null) {
                query.bind("vnfType", vnfType);
            }

            if (serviceId != null) {
                query.bind("serviceId", serviceId);
            }

            if (serviceLocation != null) {
                query.bind("serviceLocation", serviceLocation);
            }

            query.bind("createdCutoff", createdCutoff);

            serviceTypeObjects = query.list();
        }

        offset = (offset == null) ? 0 : offset;

        Integer totalCount = serviceTypeObjects.size();

        // See comment in DcaeServicesApiServiceImpl.java above similar code.
        Integer endpoint = Math.min(offset + PAGINATION_PAGE_SIZE, totalCount);
        List<DCAEServiceTypeObject> serviceTypeObjectsSliced = serviceTypeObjects.subList(offset, endpoint);

        List<DCAEServiceType> serviceTypes = new ArrayList<>();

        for (DCAEServiceTypeObject serviceTypeObject : serviceTypeObjectsSliced) {
            serviceTypes.add(createDCAEServiceType(serviceTypeObject, uriInfo));
        }

        InlineResponse200 response = new InlineResponse200();
        response.setItems(serviceTypes);
        response.setTotalCount(totalCount);

        InlineResponse200Links navigationLinks = new InlineResponse200Links();
        Integer offsetPrev = offset - PAGINATION_PAGE_SIZE;

        if (offsetPrev >= 0) {
            navigationLinks.setPreviousLink(DcaeServiceTypesApi.buildLinkForGet(uriInfo, "prev", vnfType, offsetPrev));
        }

        Integer offsetNext = offset + PAGINATION_PAGE_SIZE;

        if (offsetNext < totalCount) {
            navigationLinks.setNextLink(DcaeServiceTypesApi.buildLinkForGet(uriInfo, "next", vnfType, offsetNext));
        }

        response.setLinks(navigationLinks);

        return Response.ok().entity(response).build();
    }

    @Override
    public Response dcaeServiceTypesTypeNameGet(String typeName, UriInfo uriInfo, SecurityContext securityContext)
            throws NotFoundException {
        DCAEServiceTypesDAO serviceTypesDAO = InventoryDAOManager.getInstance().getDCAEServiceTypesDAO();
        DCAEServiceTypeObject serviceTypeObject = serviceTypesDAO.getByTypeName(typeName);

        if (serviceTypeObject == null) {
           return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(createDCAEServiceType(serviceTypeObject, uriInfo)).build();
    }

    @Override
    public Response dcaeServiceTypesTypeNamePut(String typeName, DCAEServiceTypeRequest request, UriInfo uriInfo,
                                                SecurityContext securityContext) {
        DCAEServiceTypesDAO serviceTypesDAO = InventoryDAOManager.getInstance().getDCAEServiceTypesDAO();
        DCAEServiceTypeObject serviceTypeObject = serviceTypesDAO.getByTypeName(typeName);

        if (serviceTypeObject == null) {
            // Create object from request
            serviceTypeObject = new DCAEServiceTypeObject();
            serviceTypeObject.setTypeName(typeName);
            serviceTypeObject.setOwner(request.getOwner());
            serviceTypeObject.setVnfTypes(request.getVnfTypes());
            serviceTypeObject.setServiceIds(request.getServiceIds());
            serviceTypeObject.setServiceLocations(request.getServiceLocations());
            serviceTypeObject.setBlueprintTemplate(request.getBlueprintTemplate());
            serviceTypeObject.setCreated(DateTime.now(DateTimeZone.UTC));

            serviceTypesDAO.insertNewerVersion(serviceTypeObject);

            return Response.ok().entity(createDCAEServiceType(serviceTypeObject, uriInfo)).build();
        }

        DCAEServicesDAO servicesDAO = InventoryDAOManager.getInstance().getDCAEServicesDAO();
        Integer count = servicesDAO.countByType(DCAEServiceObject.DCAEServiceStatus.RUNNING, typeName);

        LOG.info(String.format("Checked num DCAE services running: %s, %d", typeName, count));

        // Allow the updating of an existing DCAE service type IFF there are no running DCAE services for this type

        if (count > 0) {
            String message = String.format("DCAE services of type %s are still running: %d", typeName, count);
            ApiResponseMessage entity = new ApiResponseMessage(ApiResponseMessage.ERROR, message);
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            serviceTypeObject.setOwner(request.getOwner());
            serviceTypeObject.setVnfTypes(request.getVnfTypes());
            serviceTypeObject.setServiceIds(request.getServiceIds());
            serviceTypeObject.setServiceLocations(request.getServiceLocations());
            serviceTypeObject.setBlueprintTemplate(request.getBlueprintTemplate());
            serviceTypeObject.setCreated(DateTime.now(DateTimeZone.UTC));

            InventoryDAOManager.getInstance().getDCAEServiceTypeTransactionDAO().upsert(serviceTypeObject);

            return Response.ok().entity(serviceTypeObject).build();
        }
    }

}
