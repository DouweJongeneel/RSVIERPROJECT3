/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "klantadresadrestype")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Klantadresadrestype.findAll", query = "SELECT k FROM Klantadresadrestype k"),
	@NamedQuery(name = "Klantadresadrestype.findByKlantId", query = "SELECT k FROM Klantadresadrestype k WHERE k.klantadresadrestypePK.klantId = :klantId"),
	@NamedQuery(name = "Klantadresadrestype.findByAdresId", query = "SELECT k FROM Klantadresadrestype k WHERE k.klantadresadrestypePK.adresId = :adresId")})
public class Klantadresadrestype implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected KlantadresadrestypePK klantadresadrestypePK;
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

	public Klantadresadrestype(KlantadresadrestypePK klantadresadrestypePK) {
		this.klantadresadrestypePK = klantadresadrestypePK;
	}

	public Klantadresadrestype(long klantId, long adresId) {
		this.klantadresadrestypePK = new KlantadresadrestypePK(klantId, adresId);
	}

	public KlantadresadrestypePK getKlantadresadrestypePK() {
		return klantadresadrestypePK;
	}

	public void setKlantadresadrestypePK(KlantadresadrestypePK klantadresadrestypePK) {
		this.klantadresadrestypePK = klantadresadrestypePK;
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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (klantadresadrestypePK != null ? klantadresadrestypePK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Klantadresadrestype)) {
			return false;
		}
		Klantadresadrestype other = (Klantadresadrestype) object;
		if ((this.klantadresadrestypePK == null && other.klantadresadrestypePK != null) || (this.klantadresadrestypePK != null && !this.klantadresadrestypePK.equals(other.klantadresadrestypePK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Klantadresadrestype[ klantadresadrestypePK=" + klantadresadrestypePK + " ]";
	}
	
}
