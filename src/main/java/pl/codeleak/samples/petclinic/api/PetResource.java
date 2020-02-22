package pl.codeleak.samples.petclinic.api;

import pl.codeleak.samples.petclinic.api.support.PageRequest;
import pl.codeleak.samples.petclinic.api.support.ResourceSupport;
import pl.codeleak.samples.petclinic.model.Owner;
import pl.codeleak.samples.petclinic.model.Pet;
import pl.codeleak.samples.petclinic.model.PetType;
import pl.codeleak.samples.petclinic.model.Visit;
import pl.codeleak.samples.petclinic.repository.PetRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;
import java.util.function.Function;

@Path(PetResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class PetResource {

    public static final String RESOURCE_PATH = "/pets";

    @Context
    UriInfo uriInfo;

    @Inject
    PetRepository petRepository;

    @GET
    public Response getAll(@BeanParam PageRequest pageRequest) {
        return ResourceSupport.getAll(pageRequest, petRepository);
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        return ResourceSupport.getOne(id, petRepository);
    }

    @GET
    @Path("{id}/type")
    public Response getPetType(@PathParam("id") Long id) {
        return ResourceSupport.getItem(id, petRepository, (Function<Pet, PetType>) Pet::getType);
    }

    @GET
    @Path("{id}/owner")
    public Response getOwner(@PathParam("id") Long id) {
        return ResourceSupport.getItem(id, petRepository, (Function<Pet, Owner>) Pet::getOwner);
    }

    @GET
    @Path("{id}/visits")
    public Response getVisits(@PathParam("id") Long id) {
        return ResourceSupport.getItem(id, petRepository, (Function<Pet, Set<Visit>>) Pet::getVisits);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(@Valid Pet pet) {
        return ResourceSupport.create(pet, petRepository, uriInfo);
    }
}
