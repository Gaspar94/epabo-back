package com.epabo.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Gasto.
 */
@Entity
@Table(name = "gasto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gasto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @NotNull
    @Column(name = "monto_gasto", nullable = false)
    private Double montoGasto;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

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

    public Gasto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Gasto fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public Gasto fechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Double getMontoGasto() {
        return montoGasto;
    }

    public Gasto montoGasto(Double montoGasto) {
        this.montoGasto = montoGasto;
        return this;
    }

    public void setMontoGasto(Double montoGasto) {
        this.montoGasto = montoGasto;
    }

    public Long getUserId() {
        return userId;
    }

    public Gasto userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        Gasto gasto = (Gasto) o;
        if (gasto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gasto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gasto{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", montoGasto=" + getMontoGasto() +
            ", userId=" + getUserId() +
            "}";
    }
}
