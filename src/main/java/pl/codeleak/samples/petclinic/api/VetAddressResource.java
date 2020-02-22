package pl.codeleak.samples.petclinic.api;

import pl.codeleak.samples.petclinic.api.support.PageRequest;
import pl.codeleak.samples.petclinic.api.support.ResourceSupport;
import pl.codeleak.samples.petclinic.model.VetAddress;
import pl.codeleak.samples.petclinic.repository.VetAddressRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(VetAddressResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class VetAddressResource {

    public static final String RESOURCE_PATH = "/vet-addresses";

    @Context
    UriInfo uriInfo;

    @Inject
    VetAddressRepository vetAddressRepository;

    @GET
    public Response getAll(@BeanParam PageRequest pageRequest) {
        return ResourceSupport.getAll(pageRequest, vetAddressRepository);
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        return ResourceSupport.getOne(id, vetAddressRepository);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(@Valid VetAddress vetAddress) {
        vetAddressRepository.persist(vetAddress);
        return Response.ok(vetAddressRepository.findAll().list()).build();
    }
}
