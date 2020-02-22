package pl.codeleak.samples.petclinic.api;

import pl.codeleak.samples.petclinic.api.support.PageRequest;
import pl.codeleak.samples.petclinic.api.support.ResourceSupport;
import pl.codeleak.samples.petclinic.model.Owner;
import pl.codeleak.samples.petclinic.model.Pet;
import pl.codeleak.samples.petclinic.repository.OwnerRepository;
import pl.codeleak.samples.petclinic.repository.PetRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path(OwnerResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class OwnerResource {

    public static final String RESOURCE_PATH = "/owners";

    @Context
    UriInfo uriInfo;

    @Inject
    OwnerRepository ownerRepository;

    @Inject
    PetRepository petRepository;

    @GET
    public Response getAll(@BeanParam PageRequest pageRequest) {
        return ResourceSupport.getAll(pageRequest, ownerRepository);
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        return ResourceSupport.getOne(id, ownerRepository);
    }

    @GET
    @Path("{id}/pets")
    public List<Pet> getPets(@PathParam("id") Long id) {
        return petRepository.find("from Pet p join p.owner o where o.id = ?1", id).list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(@Valid Owner owner) {
        return ResourceSupport.create(owner, ownerRepository, uriInfo);
    }
}
