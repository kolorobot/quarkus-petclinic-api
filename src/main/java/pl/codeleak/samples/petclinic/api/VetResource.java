package pl.codeleak.samples.petclinic.api;

import pl.codeleak.samples.petclinic.api.support.PageRequest;
import pl.codeleak.samples.petclinic.api.support.ResourceSupport;
import pl.codeleak.samples.petclinic.model.Pet;
import pl.codeleak.samples.petclinic.model.Specialty;
import pl.codeleak.samples.petclinic.model.Vet;
import pl.codeleak.samples.petclinic.model.Visit;
import pl.codeleak.samples.petclinic.repository.SpecialtyRepository;
import pl.codeleak.samples.petclinic.repository.VetRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Path(VetResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class VetResource {

    public static final String RESOURCE_PATH = "/vets";

    @Context
    UriInfo uriInfo;

    @Inject
    VetRepository vetRepository;

    @Inject
    SpecialtyRepository specialtyRepository;

    @GET
    public Response getAll(@BeanParam PageRequest pageRequest) {
        return ResourceSupport.getAll(pageRequest, vetRepository);
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        return ResourceSupport.getOne(id, vetRepository);
    }

    @GET
    @Path("{id}/specialties")
    public Response getSpecialties(@PathParam("id") Long id) {
        return ResourceSupport.getItem(id, vetRepository, (Function<Vet, Set<Specialty>>) Vet::getSpecialties);
    }

    @GET
    @Path("{id}/visits")
    public Response getVisits(@PathParam("id") Long id) {
        return ResourceSupport.getItem(id, vetRepository, (Function<Vet, Set<Visit>>) Vet::getVisits);
    }

    @PUT
    @Path("{id}/specialties")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addSpecialty(@PathParam("id") Long id, Specialty specialty) {
        BiConsumer<Vet, Specialty> addSpecialtyToVet = (v, s) -> v.getSpecialties().add(s);
        return vetSpecialty(id, specialty, addSpecialtyToVet);
    }

    @DELETE
    @Path("{id}/specialties")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response removeSpecialty(@PathParam("id") Long id, Specialty specialty) {
        BiConsumer<Vet, Specialty> removeSpecialtyFromVet = (v, s) -> v.getSpecialties().remove(s);
        return vetSpecialty(id, specialty, removeSpecialtyFromVet);
    }

    private Response vetSpecialty(Long id, Specialty specialty, BiConsumer<Vet, Specialty> action) {
        var vetOptional = vetRepository.findByIdOptional(id);
        if (vetOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var specialtyOptional = specialtyRepository.findByIdOptional(specialty.getId());
        if (specialtyOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var vet = vetOptional.get();

        action.accept(vet, specialtyOptional.get());

        vetRepository.persist(vet);
        return Response.ok().build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Vet vet) {
        return ResourceSupport.create(vet, vetRepository, uriInfo);
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Vet vet) {
        var toUpdateOptional = vetRepository.findByIdOptional(id);
        if (toUpdateOptional.isPresent()) {
            var toUpdate = toUpdateOptional.get();
            toUpdate.setFirstName(vet.getFirstName());
            toUpdate.setLastName(vet.getLastName());
            return ResourceSupport.update(toUpdate, vetRepository, uriInfo);
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        var vet = vetRepository.findByIdOptional(id);
        vet.ifPresent(v -> vetRepository.delete(v));
        return Response.noContent().build();
    }
}
