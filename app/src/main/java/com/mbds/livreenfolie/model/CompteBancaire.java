/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbds.livreenfolie.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompteBancaire {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String numero;
    private double montant;
    private Utilisateur owner;
    private List<Operation> operations;

    public CompteBancaire() {
        operations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getOwner() {
        return owner;
    }

    public void setOwner(Utilisateur owner) {
        this.owner = owner;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void addOperation(OperationType type, double montant) {
        Operation operation = new Operation(montant, type);
        this.operations.add(operation);
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
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CompteBancaire[ id=" + id + " ]";
    }

}
