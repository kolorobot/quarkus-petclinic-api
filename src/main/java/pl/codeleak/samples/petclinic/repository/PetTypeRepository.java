package pl.codeleak.samples.petclinic.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.codeleak.samples.petclinic.model.PetType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PetTypeRepository implements PanacheRepository<PetType> {

}
