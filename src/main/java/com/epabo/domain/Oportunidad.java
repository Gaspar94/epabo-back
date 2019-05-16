package com.epabo.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Oportunidad.
 */
@Entity
@Table(name = "oportunidad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Oportunidad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @NotNull
    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    @NotNull
    @Column(name = "autor", nullable = false)
    private Long autor;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "tags", nullable = false)
    private String tags;

    @Column(name = "url_imagen")
    private String urlImagen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Oportunidad descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Oportunidad fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Oportunidad ubicacion(String ubicacion) {
        this.ubicacion = ubicacion;
        return this;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getAutor() {
        return autor;
    }

    public Oportunidad autor(Long autor) {
        this.autor = autor;
        return this;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
    }

    public String getEmail() {
        return email;
    }

    public Oportunidad email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTags() {
        return tags;
    }

    public Oportunidad tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Oportunidad urlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
        return this;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Oportunidad oportunidad = (Oportunidad) o;
        if (oportunidad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), oportunidad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Oportunidad{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", autor=" + getAutor() +
            ", email='" + getEmail() + "'" +
            ", tags='" + getTags() + "'" +
            ", urlImagen='" + getUrlImagen() + "'" +
            "}";
    }
}
