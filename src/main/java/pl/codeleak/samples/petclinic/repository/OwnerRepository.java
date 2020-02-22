package pl.codeleak.samples.petclinic.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.codeleak.samples.petclinic.model.Owner;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OwnerRepository implements PanacheRepository<Owner> {
    List<Owner> findByLastName(String lastName) {
        return list("lastName", lastName);
    }
}
