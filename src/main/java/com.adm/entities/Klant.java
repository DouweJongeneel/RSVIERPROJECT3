/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.enterprise.inject.Model;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "klant")
@NamedQueries({
	@NamedQuery(name = "Klant.findAll", query = "SELECT k FROM Klant k"),
	@NamedQuery(name = "Klant.findById", query = "SELECT k FROM Klant k WHERE k.id = :id")})
@Model
public class Klant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "voornaam")
	private String voornaam;

	@NotNull
	@Size(max = 255)
	@Column(name = "tussenvoegsel")
	private String tussenvoegsel;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "achternaam")
	private String achternaam;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "password")
	private String password;
	
	@Transient
	private String tempPassword;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "email")
	private String email;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "datumAanmaak")
	private String datumAanmaak;

	@Size(min = 1, max = 255)
	@Column(name = "datumGewijzigd")
	private String datumGewijzigd;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "klantActief")
	private String klantActief;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "klantRol")
	private String klantRol;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klantId")
	private Collection<Betaling> betalingCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klant")
	private Collection<Klantadresadrestype> klantadresadrestypeCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klantId")
	private Collection<Bestelling> bestellingCollection;

	@Transient
	boolean canEdit = false; 
	
	public Klant() {
		datumAanmaak = new Date(System.currentTimeMillis()).toString();
		this.klantActief = "1";
		this.klantRol = "ROLE_USER";
	}

	public Klant(Long id) {
		this.id = id;
		this.klantActief = "1";
		datumAanmaak = new Date(System.currentTimeMillis()).toString();
	}

	public Klant(String voornaam, String tussenvoegsel, String achternaam, String email, String password) {
		this.achternaam = achternaam;
		this.email = email;
		this.klantActief = "1";
		this.klantRol = "ROLE_USER";
		this.password = password;
		this.tussenvoegsel = tussenvoegsel;
		this.voornaam = voornaam;
		datumAanmaak = new Date(System.currentTimeMillis()).toString();
	}

	public Klant(Long id, String achternaam, String datumAanmaak, String datumGewijzigd, String email, String klantActief, String klantRol, String password, String tussenvoegsel, String voornaam) {
		this.id = id;
		this.achternaam = achternaam;
		this.datumAanmaak = datumAanmaak;
		this.datumGewijzigd = datumGewijzigd;
		this.email = email;
		this.klantActief = klantActief;
		this.klantRol = klantRol;
		this.password = password;
		this.tussenvoegsel = tussenvoegsel;
		this.voornaam = voornaam;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKlantActief() {
		return klantActief;
	}

	public void setKlantActief(String klantActief) {
		this.klantActief = klantActief;
	}

	public String getKlantRol() {
		return klantRol;
	}

	public void setKlantRol(String klantRol) {
		this.klantRol = klantRol;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public String getTussenvoegsel() {
		return tussenvoegsel;
	}

	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public Collection<Betaling> getBetalingCollection() {
		return betalingCollection;
	}

	public void setBetalingCollection(Collection<Betaling> betalingCollection) {
		this.betalingCollection = betalingCollection;
	}

	public Collection<Klantadresadrestype> getKlantadresadrestypeCollection() {
		return klantadresadrestypeCollection;
	}

	public void setKlantadresadrestypeCollection(Collection<Klantadresadrestype> klantadresadrestypeCollection) {
		this.klantadresadrestypeCollection = klantadresadrestypeCollection;
	}

	public Collection<Bestelling> getBestellingCollection() {
		return bestellingCollection;
	}

	public void setBestellingCollection(Collection<Bestelling> bestellingCollection) {
		this.bestellingCollection = bestellingCollection;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Klant)) {
			return false;
		}
		Klant other = (Klant) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Voornaam: %s\nAchternaam: %s\nTussenvoegsel: %s\nEmail: %s\nWachtwoord: %s", voornaam, achternaam, tussenvoegsel, email, password);
	}

}
