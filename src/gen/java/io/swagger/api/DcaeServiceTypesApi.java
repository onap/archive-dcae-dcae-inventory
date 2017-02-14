package io.swagger.api;

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

import io.swagger.api.factories.DcaeServiceTypesApiServiceFactory;

import io.swagger.annotations.ApiParam;

import io.swagger.model.InlineResponse200;
import io.swagger.model.DCAEServiceType;
import io.swagger.model.DCAEServiceTypeRequest;

import javax.validation.Valid;
import javax.ws.rs.core.*;
import javax.ws.rs.*;

@Path("/dcae-service-types")
@Consumes({"application/json", "application/vnd.dcae.inventory.v1+json"})
@Produces({"application/json", "application/vnd.dcae.inventory.v1+json"})
@io.swagger.annotations.Api(description = "the dcae-service-types API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-04-18T20:16:22.119Z")
public class DcaeServiceTypesApi {
    private final DcaeServiceTypesApiService delegate = DcaeServiceTypesApiServiceFactory.getDcaeServiceTypesApi();

    @Context
    UriInfo uriInfo;

    public static Link buildLinkForGet(UriInfo uriInfo, String rel, String vnfType, Integer offset) {
        UriBuilder ub = uriInfo.getBaseUriBuilder().path(DcaeServiceTypesApi.class)
                .path(DcaeServiceTypesApi.class, "dcaeServiceTypesGet");

        if (vnfType != null) {
            ub.queryParam("vnfType", vnfType);
        }
        if (offset != null) {
            ub.queryParam("offset", offset);
        }

        Link.Builder lb = Link.fromUri(ub.build());
        lb.rel(rel);
        return lb.build();
    }

    @GET
    @Path("/")
    @Consumes({"application/json", "application/vnd.dcae.inventory.v1+json"})
    @Produces({"application/json", "application/vnd.dcae.inventory.v1+json"})
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get a list of `DCAEServiceType` objects.", response = InlineResponse200.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "List of `DCAEServiceType` objects", response = InlineResponse200.class)})
    public Response dcaeServiceTypesGet(
            @ApiParam(value = "") @QueryParam("vnfType") String vnfType,
            @ApiParam(value = "") @QueryParam("serviceId") String serviceId,
            @ApiParam(value = "") @QueryParam("serviceLocation") String serviceLocation,
            @ApiParam(value = "Query resultset offset used for pagination (zero-based)") @QueryParam("offset") Integer offset,
            @Context SecurityContext securityContext)
            throws NotFoundException {
        return delegate.dcaeServiceTypesGet(vnfType, serviceId, serviceLocation, offset, uriInfo, securityContext);
    }

    public static Link buildLinkForGet(UriInfo uriInfo, String rel, String typeName) {
        // This same method can be used for PUTs as well

        UriBuilder ub = uriInfo.getBaseUriBuilder().path(DcaeServiceTypesApi.class)
                .path(DcaeServiceTypesApi.class, "dcaeServiceTypesTypeNameGet");
        Link.Builder lb = Link.fromUri(ub.build(typeName));
        lb.rel(rel);
        return lb.build();
    }

    @GET
    @Path("/{typeName}")
    @Consumes({"application/json", "application/vnd.dcae.inventory.v1+json"})
    @Produces({"application/json", "application/vnd.dcae.inventory.v1+json"})
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get a `DCAEServiceType` object.", response = DCAEServiceType.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Single `DCAEServiceType` object", response = DCAEServiceType.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found", response = DCAEServiceType.class)})
    public Response dcaeServiceTypesTypeNameGet(
            @ApiParam(value = "", required = true) @PathParam("typeName") String typeName,
            @Context SecurityContext securityContext)
            throws NotFoundException {
        return delegate.dcaeServiceTypesTypeNameGet(typeName, uriInfo, securityContext);
    }

    @PUT
    @Path("/{typeName}")
    @Consumes({"application/json", "application/vnd.dcae.inventory.v1+json"})
    @Produces({"application/json", "application/vnd.dcae.inventory.v1+json"})
    @io.swagger.annotations.ApiOperation(value = "", notes = "Inserts a new `DCAEServiceType` or updates an existing instance. Updates are only allowed iff there are no running DCAE services of the requested type,",
            response = DCAEServiceType.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Single `DCAEServiceType` object.", response = DCAEServiceType.class),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request provided.", response = ApiResponseMessage.class),
            @io.swagger.annotations.ApiResponse(code = 409, message = "Failed to update because there are still DCAE services of the requested type running.", response = ApiResponseMessage.class)})
    public Response dcaeServiceTypesTypeNamePut(
            @ApiParam(value = "", required = true) @PathParam("typeName") String typeName,
            @ApiParam(value = "", required = true) @Valid DCAEServiceTypeRequest request,
            @Context SecurityContext securityContext)
            throws NotFoundException {
        return delegate.dcaeServiceTypesTypeNamePut(typeName, request, uriInfo, securityContext);
    }
}
