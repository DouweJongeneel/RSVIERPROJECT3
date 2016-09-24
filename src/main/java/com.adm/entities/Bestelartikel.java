/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "bestelartikel")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Bestelartikel.findAll", query = "SELECT b FROM Bestelartikel b"),
	@NamedQuery(name = "Bestelartikel.findById", query = "SELECT b FROM Bestelartikel b WHERE b.id = :id"),
	@NamedQuery(name = "Bestelartikel.findByBestellingId", query = "SELECT b FROM Bestelartikel b WHERE b.bestellingId = :bestellingId")})
public class Bestelartikel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@NotNull
	@Column(name = "aantal")
	private int aantal;

	@JoinColumn(name = "artikelId", referencedColumnName = "artikelId")
	@ManyToOne(optional = false)
	private Artikel artikelId;

	@JoinColumn(name = "BestellingId", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Bestelling bestellingId;

	@JoinColumn(name = "prijsId", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Prijs prijsId;


	public Bestelartikel() {
	}

	public Bestelartikel(Prijs prijs, Artikel artikel, int aantal) {
		this.artikelId = artikel;
		this.prijsId = prijs;
		this.aantal = aantal;
	}

	public String getTotaal(){
		return String.format("%1.2f", prijsId.getPrijs().multiply(new BigDecimal("" + aantal)).doubleValue());
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAantal() {
		return aantal;
	}

	public void setAantal(int aantal) {
		this.aantal = aantal;
	}

	public Artikel getArtikelId() {
		return artikelId;
	}

	public void setArtikelId(Artikel artikelId) {
		this.artikelId = artikelId;
	}

	public Bestelling getBestellingId() {
		return bestellingId;
	}

	public void setBestellingId(Bestelling bestellingId) {
		this.bestellingId = bestellingId;
	}

	public Prijs getPrijsId() {
		return prijsId;
	}

	public void setPrijsId(Prijs prijsId) {
		this.prijsId = prijsId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Bestelartikel)) {
			return false;
		}
		
		boolean artikelGelijk = artikelId.getArtikelId() == ((Bestelartikel)object).getArtikelId().getArtikelId();
		boolean prijsGelijk = ((Bestelartikel)object).getPrijsId().getId() == this.prijsId.getId();
		return  artikelGelijk && prijsGelijk;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Bestelartikel[ id=" + id + " ]";
	}

}
