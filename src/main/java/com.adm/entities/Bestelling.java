/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "bestelling")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Bestelling.findAll", query = "SELECT b FROM Bestelling b"),
	@NamedQuery(name = "Bestelling.findById", query = "SELECT b FROM Bestelling b WHERE b.id = :id"),
	@NamedQuery(name = "Bestelling.findByBestelNummer", query = "SELECT b FROM Bestelling b WHERE b.bestelNummer = :bestelNummer"),
	@NamedQuery(name = "Bestelling.findByBestellingActief", query = "SELECT b FROM Bestelling b WHERE b.bestellingActief = :bestellingActief"),
	@NamedQuery(name = "Bestelling.findByDatumAanmaak", query = "SELECT b FROM Bestelling b WHERE b.datumAanmaak = :datumAanmaak")})
public class Bestelling implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "bestelNummer")
	private String bestelNummer;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "bestellingActief")
	private boolean bestellingActief;
	
	@Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datumAanmaak")
	private Date datumAanmaak;
	
	@JoinColumn(name = "klant_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
	private Klant klant;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bestellingId")
	private Collection<Bestelartikel> bestelartikelCollection;
	
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private Factuur factuur;

	public Bestelling() {
		datumAanmaak= new Date(System.currentTimeMillis());
		bestellingActief = true;
	}

	public Bestelling(Long id) {
		this.id = id;
	}

	public Bestelling(Long id, String bestelNummer, boolean bestellingActief, Date datumAanmaak) {
		this.id = id;
		this.bestelNummer = bestelNummer;
		this.bestellingActief = bestellingActief;
		this.datumAanmaak = datumAanmaak;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBestelNummer() {
		return bestelNummer;
	}

	public void setBestelNummer(String bestelNummer) {
		this.bestelNummer = bestelNummer;
	}

	public boolean getBestellingActief() {
		return bestellingActief;
	}

	public void setBestellingActief(boolean bestellingActief) {
		this.bestellingActief = bestellingActief;
	}

	public Date getDatumAanmaak() {
		return datumAanmaak;
	}

	public void setDatumAanmaak(Date datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}

	public Klant getKlant() {
		return klant;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	@XmlTransient
	public Collection<Bestelartikel> getBestelartikelCollection() {
		return bestelartikelCollection;
	}

	public void setBestelartikelCollection(Collection<Bestelartikel> bestelartikelCollection) {
		this.bestelartikelCollection = bestelartikelCollection;
	}

	@XmlTransient
	public Factuur getFactuur() {
		return factuur;
	}

	public void setFactuur(Factuur factuur) {
		this.factuur = factuur;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Bestelling)) {
			return false;
		}
		Bestelling other = (Bestelling) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Bestelling[ id=" + id + " ]";
	}
	
}
