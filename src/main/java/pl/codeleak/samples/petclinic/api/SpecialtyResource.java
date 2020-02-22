package pl.codeleak.samples.petclinic.api;

import io.quarkus.panache.common.Page;
import pl.codeleak.samples.petclinic.model.Specialty;
import pl.codeleak.samples.petclinic.model.Vet;
import pl.codeleak.samples.petclinic.repository.SpecialtyRepository;
import pl.codeleak.samples.petclinic.repository.VetRepository;
import pl.codeleak.samples.petclinic.api.support.PageRequest;
import pl.codeleak.samples.petclinic.api.support.ResourceSupport;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(SpecialtyResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class SpecialtyResource {

    public static final String RESOURCE_PATH = "/specialties";

    @Inject
    SpecialtyRepository specialtyRepository;

    @Inject
    VetRepository vetRepository;

    @GET
    public Response getAll(@BeanParam PageRequest pageRequest) {
        return ResourceSupport.getAll(pageRequest, specialtyRepository);
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        return ResourceSupport.getOne(id, specialtyRepository);
    }

    @GET
    @Path("{id}/vets")
    public List<Vet> getVets(@PathParam("id") Long id,
                             @BeanParam PageRequest pageRequest) {
        return vetRepository
                .find("from Vet v join v.specialties s where v.id = ?1", id)
                .page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize()))
                .list();
    }
}
