/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "betaling")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Betaling.findAll", query = "SELECT b FROM Betaling b"),
	@NamedQuery(name = "Betaling.findById", query = "SELECT b FROM Betaling b WHERE b.id = :id"),
	@NamedQuery(name = "Betaling.findByBetaalDatum", query = "SELECT b FROM Betaling b WHERE b.betaalDatum = :betaalDatum"),
	@NamedQuery(name = "Betaling.findByBetalingsGegevens", query = "SELECT b FROM Betaling b WHERE b.betalingsGegevens = :betalingsGegevens")})
public class Betaling implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
	private Long id;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "betaalDatum")
    @Temporal(TemporalType.DATE)
	private Date betaalDatum;
	
	@Size(max = 255)
    @Column(name = "betalingsGegevens")
	private String betalingsGegevens;
	
    @OneToOne(optional = false)
	private Factuur factuurId;
	
	@JoinColumn(name = "klant_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Klant klant;
	
	@Column(name="betaalwijze")
	private String betaalwijze;

	public Betaling() {
		betaalDatum = new Date(System.currentTimeMillis());
	}

	public Betaling(Long id) {
		this.id = id;
	}

	public Betaling(Long id, Date betaalDatum) {
		this.id = id;
		this.betaalDatum = betaalDatum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBetaalDatum() {
		return betaalDatum;
	}

	public void setBetaalDatum(Date betaalDatum) {
		this.betaalDatum = betaalDatum;
	}

	public String getBetalingsGegevens() {
		return betalingsGegevens;
	}

	public void setBetalingsGegevens(String betalingsGegevens) {
		this.betalingsGegevens = betalingsGegevens;
	}

	public Factuur getFactuurId() {
		return factuurId;
	}

	public void setFactuurId(Factuur factuurId) {
		this.factuurId = factuurId;
	}

	public Klant getKlant() {
		return klant;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public String getBetaalwijze() {
		return betaalwijze;
	}

	public void setBetaalwijze(String betaalwijze) {
		this.betaalwijze = betaalwijze;
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
		if (!(object instanceof Betaling)) {
			return false;
		}
		Betaling other = (Betaling) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Betaling[ id=" + id + " ]";
	}
	
}
