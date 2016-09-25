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
	@NamedQuery(name = "Klant.findByEmail", query = "SELECT k FROM Klant k WHERE k.email = :email AND k.password = :password")})
public class Klant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	
	@Size(min = 1, max = 255)
	@Column(name = "voornaam")
	private String voornaam;

	
	@Size(max = 255)
	@Column(name = "tussenvoegsel")
	private String tussenvoegsel;

	@Size(min = 1, max = 255)
	@Column(name = "achternaam")
	private String achternaam;

	@Size(min = 1, max = 255)
	@Column(name = "password")
	private String password;

	@Transient
	private String tempPassword = "********";

	@Size(min = 1, max = 255)
	@Column(name = "email")
	private String email;

	@Size(min = 1, max = 255)
	@Column(name = "datumAanmaak")
	private String datumAanmaak;

	@Size(min = 1, max = 255)
	@Column(name = "datumGewijzigd")
	private String datumGewijzigd;

	@Column(name = "klantActief")
	private boolean klantActief;

	@Size(min = 1, max = 255)
	@Column(name = "klantRol")
	private String klantRol = "ROLE_USER";

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klant")
	private Collection<Betaling> betalingCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klant")
	private Collection<Klantadresadrestype> klantadresadrestypeCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klant")
	private Collection<Bestelling> bestellingCollection;

	@Transient
	boolean canEdit = false;

	@Transient
	private boolean adminRechten;

	public Klant() {
		datumAanmaak = new Date(System.currentTimeMillis()).toString();
		this.klantActief = true;
		this.klantRol = "ROLE_USER";
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

	public boolean isKlantActief() {
		return klantActief;
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

	public boolean isAdminRechten() {
		return klantRol.equals("ROLE_ADMINISTRATOR");
	}

	public void setAdminRechten(boolean adminRechten) {
		if (this.isAdminRechten() != adminRechten) {
			datumGewijzigd = new Date(System.currentTimeMillis()).toString();
		}
		klantRol = adminRechten ? "ROLE_ADMINISTRATOR" : "ROLE_USER";
	}

	public void setKlantActief(boolean klantActief) {
		if (this.isKlantActief() != klantActief) {
			datumGewijzigd = new Date(System.currentTimeMillis()).toString();
		}
		this.klantActief = klantActief;
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
