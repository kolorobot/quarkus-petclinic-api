package pl.codeleak.samples.petclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vets",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"first_name", "last_name"})
)
public class Vet extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    @JsonIgnore
    private Set<Specialty> specialties;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vet", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Visit> visits;

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public Set<Visit> getVisits() {
        return visits;
    }
}
