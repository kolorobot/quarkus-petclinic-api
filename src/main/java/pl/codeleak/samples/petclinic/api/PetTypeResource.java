package pl.codeleak.samples.petclinic.api;

import pl.codeleak.samples.petclinic.model.Pet;
import pl.codeleak.samples.petclinic.model.PetType;
import pl.codeleak.samples.petclinic.model.Specialty;
import pl.codeleak.samples.petclinic.model.Vet;
import pl.codeleak.samples.petclinic.repository.PetRepository;
import pl.codeleak.samples.petclinic.repository.PetTypeRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("pet-types")
@Produces(MediaType.APPLICATION_JSON)
public class PetTypeResource {

    public static final String RESOURCE_PATH = "/pet-types";

    @Inject
    PetTypeRepository petTypeRepository;

    @Inject
    PetRepository petRepository;

    @GET
    public List<PetType> getAll() {
        return petTypeRepository.listAll();
    }

    @GET
    @Path("{id}")
    public PetType getOne(@PathParam("id") Long id) {
        return petTypeRepository.findById(id);
    }

    @GET
    @Path("{id}/pets")
    public List<Pet> getPets(@PathParam("id") Long id) {
        return petRepository.find("from Pet p join p.type t where t.id = ?1", id)
                .list();
    }
}
