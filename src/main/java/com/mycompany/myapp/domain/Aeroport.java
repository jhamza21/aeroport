package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Aeroport.
 */
@Entity
@Table(name = "aeroport")
public class Aeroport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "ville", nullable = false)
    private String ville;

    @NotNull
    @Column(name = "max_avion", nullable = false)
    private Integer maxAvion;

    @OneToMany(mappedBy = "aeroport")
    private Set<Avion> avions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Aeroport name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVille() {
        return ville;
    }

    public Aeroport ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getMaxAvion() {
        return maxAvion;
    }

    public Aeroport maxAvion(Integer maxAvion) {
        this.maxAvion = maxAvion;
        return this;
    }

    public void setMaxAvion(Integer maxAvion) {
        this.maxAvion = maxAvion;
    }

    public Set<Avion> getAvions() {
        return avions;
    }

    public Aeroport avions(Set<Avion> avions) {
        this.avions = avions;
        return this;
    }

    public Aeroport addAvion(Avion avion) {
        this.avions.add(avion);
        avion.setAeroport(this);
        return this;
    }

    public Aeroport removeAvion(Avion avion) {
        this.avions.remove(avion);
        avion.setAeroport(null);
        return this;
    }

    public void setAvions(Set<Avion> avions) {
        this.avions = avions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aeroport)) {
            return false;
        }
        return id != null && id.equals(((Aeroport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aeroport{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ville='" + getVille() + "'" +
            ", maxAvion=" + getMaxAvion() +
            "}";
    }
}
