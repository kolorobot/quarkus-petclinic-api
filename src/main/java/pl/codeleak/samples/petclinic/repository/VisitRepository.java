package pl.codeleak.samples.petclinic.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.codeleak.samples.petclinic.model.Visit;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VisitRepository implements PanacheRepository<Visit> {

}
