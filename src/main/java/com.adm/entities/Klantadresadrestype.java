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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "klantadresadrestype")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Klantadresadrestype.findAll", query = "SELECT k FROM Klantadresadrestype k")})
public class Klantadresadrestype implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "adresId", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Adres adres;
	@JoinColumn(name = "klantId", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Klant klant;
	@JoinColumn(name = "adresTypeId", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Adrestype adresTypeId;

	public Klantadresadrestype() {
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public Klant getKlant() {
		return klant;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public Adrestype getAdresTypeId() {
		return adresTypeId;
	}

	public void setAdresTypeId(Adrestype adresTypeId) {
		this.adresTypeId = adresTypeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (klant != null && adres != null ? klant.hashCode() + adres.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		Klantadresadrestype other = (Klantadresadrestype) object;
		if (!(object instanceof Klantadresadrestype)) {
			return false;
		}
		
		if ((id == null && other.getId() != null) || (this.id != null && !this.id.equals(other))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Klantadresadrestype[ klantadresadrestypePK=" + klant + " " + adres + " ]";
	}

}
