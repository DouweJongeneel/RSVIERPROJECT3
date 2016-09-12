/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "adres")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Adres.findAll", query = "SELECT a FROM Adres a"),
	@NamedQuery(name = "Adres.findById", query = "SELECT a FROM Adres a WHERE a.id = :id"),
	@NamedQuery(name = "Adres.findByAdresActief", query = "SELECT a FROM Adres a WHERE a.adresActief = :adresActief"),
	@NamedQuery(name = "Adres.findByDatumAanmaak", query = "SELECT a FROM Adres a WHERE a.datumAanmaak = :datumAanmaak"),
	@NamedQuery(name = "Adres.findByDatumGewijzigd", query = "SELECT a FROM Adres a WHERE a.datumGewijzigd = :datumGewijzigd"),
	@NamedQuery(name = "Adres.findByHuisnummer", query = "SELECT a FROM Adres a WHERE a.huisnummer = :huisnummer"),
	@NamedQuery(name = "Adres.findByPostcode", query = "SELECT a FROM Adres a WHERE a.postcode = :postcode"),
	@NamedQuery(name = "Adres.findByStraatnaam", query = "SELECT a FROM Adres a WHERE a.straatnaam = :straatnaam"),
	@NamedQuery(name = "Adres.findByToevoeging", query = "SELECT a FROM Adres a WHERE a.toevoeging = :toevoeging"),
	@NamedQuery(name = "Adres.findByWoonplaats", query = "SELECT a FROM Adres a WHERE a.woonplaats = :woonplaats")})
public class Adres implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
	private Long id;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "adresActief")
	private String adresActief;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "datumAanmaak")
	private String datumAanmaak;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "datumGewijzigd")
	private String datumGewijzigd;
	@Basic(optional = false)
    @NotNull
    @Column(name = "huisnummer")
	private int huisnummer;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "postcode")
	private String postcode;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "straatnaam")
	private String straatnaam;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "toevoeging")
	private String toevoeging;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "woonplaats")
	private String woonplaats;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adres")
	private Collection<Klantadresadrestype> klantadresadrestypeCollection;
	@JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne
	private Adrestype typeId;

	public Adres() {
	}

	public Adres(Long id) {
		this.id = id;
	}

	public Adres(Long id, String adresActief, String datumAanmaak, String datumGewijzigd, int huisnummer, String postcode, String straatnaam, String toevoeging, String woonplaats) {
		this.id = id;
		this.adresActief = adresActief;
		this.datumAanmaak = datumAanmaak;
		this.datumGewijzigd = datumGewijzigd;
		this.huisnummer = huisnummer;
		this.postcode = postcode;
		this.straatnaam = straatnaam;
		this.toevoeging = toevoeging;
		this.woonplaats = woonplaats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdresActief() {
		return adresActief;
	}

	public void setAdresActief(String adresActief) {
		this.adresActief = adresActief;
	}

	public String getDatumAanmaak() {
		return datumAanmaak;
	}

	public void setDatumAanmaak(String datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}

	public String getDatumGewijzigd() {
		return datumGewijzigd;
	}

	public void setDatumGewijzigd(String datumGewijzigd) {
		this.datumGewijzigd = datumGewijzigd;
	}

	public int getHuisnummer() {
		return huisnummer;
	}

	public void setHuisnummer(int huisnummer) {
		this.huisnummer = huisnummer;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getStraatnaam() {
		return straatnaam;
	}

	public void setStraatnaam(String straatnaam) {
		this.straatnaam = straatnaam;
	}

	public String getToevoeging() {
		return toevoeging;
	}

	public void setToevoeging(String toevoeging) {
		this.toevoeging = toevoeging;
	}

	public String getWoonplaats() {
		return woonplaats;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}

	@XmlTransient
	public Collection<Klantadresadrestype> getKlantadresadrestypeCollection() {
		return klantadresadrestypeCollection;
	}

	public void setKlantadresadrestypeCollection(Collection<Klantadresadrestype> klantadresadrestypeCollection) {
		this.klantadresadrestypeCollection = klantadresadrestypeCollection;
	}

	public Adrestype getTypeId() {
		return typeId;
	}

	public void setTypeId(Adrestype typeId) {
		this.typeId = typeId;
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
		if (!(object instanceof Adres)) {
			return false;
		}
		Adres other = (Adres) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Adres[ id=" + id + " ]";
	}
	
}
