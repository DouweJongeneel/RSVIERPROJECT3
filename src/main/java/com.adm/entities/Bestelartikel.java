/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
	@NamedQuery(name = "Bestelartikel.findByAantal", query = "SELECT b FROM Bestelartikel b WHERE b.aantal = :aantal")})
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

	public Bestelartikel(Long id) {
		this.id = id;
	}

	public Bestelartikel(Long id, int aantal) {
		this.id = id;
		this.aantal = aantal;
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
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Bestelartikel)) {
			return false;
		}
		Bestelartikel other = (Bestelartikel) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Bestelartikel[ id=" + id + " ]";
	}
	
}
