package pl.codeleak.samples.petclinic.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.codeleak.samples.petclinic.model.Specialty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SpecialtyRepository implements PanacheRepository<Specialty> {

}
