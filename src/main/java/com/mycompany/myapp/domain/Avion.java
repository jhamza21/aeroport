package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Avion.
 */
@Entity
@Table(name = "avion")
public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false, unique = true)
    private String matricule;

    @NotNull
    @Column(name = "company", nullable = false)
    private String company;

    @NotNull
    @Column(name = "date_arr", nullable = false)
    private ZonedDateTime dateArr;

    @NotNull
    @Column(name = "date_dep", nullable = false)
    private ZonedDateTime dateDep;

    @OneToMany(mappedBy = "avion")
    private Set<Personnel> personnel = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "avions", allowSetters = true)
    private Aeroport aeroport;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Avion matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCompany() {
        return company;
    }

    public Avion company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ZonedDateTime getDateArr() {
        return dateArr;
    }

    public Avion dateArr(ZonedDateTime dateArr) {
        this.dateArr = dateArr;
        return this;
    }

    public void setDateArr(ZonedDateTime dateArr) {
        this.dateArr = dateArr;
    }

    public ZonedDateTime getDateDep() {
        return dateDep;
    }

    public Avion dateDep(ZonedDateTime dateDep) {
        this.dateDep = dateDep;
        return this;
    }

    public void setDateDep(ZonedDateTime dateDep) {
        this.dateDep = dateDep;
    }

    public Set<Personnel> getPersonnel() {
        return personnel;
    }

    public Avion personnel(Set<Personnel> personnel) {
        this.personnel = personnel;
        return this;
    }

    public Avion addPersonnel(Personnel personnel) {
        this.personnel.add(personnel);
        personnel.setAvion(this);
        return this;
    }

    public Avion removePersonnel(Personnel personnel) {
        this.personnel.remove(personnel);
        personnel.setAvion(null);
        return this;
    }

    public void setPersonnel(Set<Personnel> personnel) {
        this.personnel = personnel;
    }

    public Aeroport getAeroport() {
        return aeroport;
    }

    public Avion aeroport(Aeroport aeroport) {
        this.aeroport = aeroport;
        return this;
    }

    public void setAeroport(Aeroport aeroport) {
        this.aeroport = aeroport;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avion)) {
            return false;
        }
        return id != null && id.equals(((Avion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avion{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", company='" + getCompany() + "'" +
            ", dateArr='" + getDateArr() + "'" +
            ", dateDep='" + getDateDep() + "'" +
            "}";
    }
}
