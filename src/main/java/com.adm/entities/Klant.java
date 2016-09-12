/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "klant")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Klant.findAll", query = "SELECT k FROM Klant k"),
	@NamedQuery(name = "Klant.findById", query = "SELECT k FROM Klant k WHERE k.id = :id"),
	@NamedQuery(name = "Klant.findByAchternaam", query = "SELECT k FROM Klant k WHERE k.achternaam = :achternaam"),
	@NamedQuery(name = "Klant.findByDatumAanmaak", query = "SELECT k FROM Klant k WHERE k.datumAanmaak = :datumAanmaak"),
	@NamedQuery(name = "Klant.findByDatumGewijzigd", query = "SELECT k FROM Klant k WHERE k.datumGewijzigd = :datumGewijzigd"),
	@NamedQuery(name = "Klant.findByEmail", query = "SELECT k FROM Klant k WHERE k.email = :email"),
	@NamedQuery(name = "Klant.findByKlantActief", query = "SELECT k FROM Klant k WHERE k.klantActief = :klantActief"),
	@NamedQuery(name = "Klant.findByKlantRol", query = "SELECT k FROM Klant k WHERE k.klantRol = :klantRol"),
	@NamedQuery(name = "Klant.findByPassword", query = "SELECT k FROM Klant k WHERE k.password = :password"),
	@NamedQuery(name = "Klant.findByTussenvoegsel", query = "SELECT k FROM Klant k WHERE k.tussenvoegsel = :tussenvoegsel"),
	@NamedQuery(name = "Klant.findByVoornaam", query = "SELECT k FROM Klant k WHERE k.voornaam = :voornaam")})
public class Klant implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
	private Long id;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "achternaam")
	private String achternaam;
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
	// @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
	private String email;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "klantActief")
	private String klantActief;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "klantRol")
	private String klantRol;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
	private String password;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tussenvoegsel")
	private String tussenvoegsel;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "voornaam")
	private String voornaam;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klantId")
	private Collection<Betaling> betalingCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klant")
	private Collection<Klantadresadrestype> klantadresadrestypeCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "klantId")
	private Collection<Bestelling> bestellingCollection;

	public Klant() {
	}

	public Klant(Long id) {
		this.id = id;
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

	@XmlTransient
	public Collection<Betaling> getBetalingCollection() {
		return betalingCollection;
	}

	public void setBetalingCollection(Collection<Betaling> betalingCollection) {
		this.betalingCollection = betalingCollection;
	}

	@XmlTransient
	public Collection<Klantadresadrestype> getKlantadresadrestypeCollection() {
		return klantadresadrestypeCollection;
	}

	public void setKlantadresadrestypeCollection(Collection<Klantadresadrestype> klantadresadrestypeCollection) {
		this.klantadresadrestypeCollection = klantadresadrestypeCollection;
	}

	@XmlTransient
	public Collection<Bestelling> getBestellingCollection() {
		return bestellingCollection;
	}

	public void setBestellingCollection(Collection<Bestelling> bestellingCollection) {
		this.bestellingCollection = bestellingCollection;
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
		return "com.mycompany.rsvierproject3.Klant[ id=" + id + " ]";
	}
	
}
