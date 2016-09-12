/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
@Table(name = "factuur")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Factuur.findAll", query = "SELECT f FROM Factuur f"),
	@NamedQuery(name = "Factuur.findById", query = "SELECT f FROM Factuur f WHERE f.id = :id"),
	@NamedQuery(name = "Factuur.findByFactureringsDatum", query = "SELECT f FROM Factuur f WHERE f.factureringsDatum = :factureringsDatum"),
	@NamedQuery(name = "Factuur.findByFactuurNummer", query = "SELECT f FROM Factuur f WHERE f.factuurNummer = :factuurNummer")})
public class Factuur implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
	private Long id;
	@Basic(optional = false)
    @NotNull
    @Column(name = "factureringsDatum")
    @Temporal(TemporalType.TIMESTAMP)
	private Date factureringsDatum;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "factuurNummer")
	private String factuurNummer;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "factuurId")
	private Collection<Betaling> betalingCollection;
	@JoinColumn(name = "bestelling_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Bestelling bestellingId;

	public Factuur() {
	}

	public Factuur(Long id) {
		this.id = id;
	}

	public Factuur(Long id, Date factureringsDatum, String factuurNummer) {
		this.id = id;
		this.factureringsDatum = factureringsDatum;
		this.factuurNummer = factuurNummer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFactureringsDatum() {
		return factureringsDatum;
	}

	public void setFactureringsDatum(Date factureringsDatum) {
		this.factureringsDatum = factureringsDatum;
	}

	public String getFactuurNummer() {
		return factuurNummer;
	}

	public void setFactuurNummer(String factuurNummer) {
		this.factuurNummer = factuurNummer;
	}

	@XmlTransient
	public Collection<Betaling> getBetalingCollection() {
		return betalingCollection;
	}

	public void setBetalingCollection(Collection<Betaling> betalingCollection) {
		this.betalingCollection = betalingCollection;
	}

	public Bestelling getBestellingId() {
		return bestellingId;
	}

	public void setBestellingId(Bestelling bestellingId) {
		this.bestellingId = bestellingId;
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
		if (!(object instanceof Factuur)) {
			return false;
		}
		Factuur other = (Factuur) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Factuur[ id=" + id + " ]";
	}
	
}
