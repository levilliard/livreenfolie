package com.mbds.livreenfolie.model;

import java.io.Serializable;
import java.util.Date;

public class Paiement {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Double montant;
    private Date datepaiment;

    public Paiement() {
    }

    public Paiement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDatepaiment() {
        return datepaiment;
    }

    public void setDatepaiment(Date datepaiment) {
        this.datepaiment = datepaiment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paiement)) {
            return false;
        }
        Paiement other = (Paiement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Paiement[ id=" + id + " ]";
    }
    
}
