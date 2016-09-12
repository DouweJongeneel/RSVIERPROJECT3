/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "prijsartikel")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Prijsartikel.findAll", query = "SELECT p FROM Prijsartikel p"),
	@NamedQuery(name = "Prijsartikel.findByPrijsId", query = "SELECT p FROM Prijsartikel p WHERE p.prijsId = :prijsId")})
public class Prijsartikel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "prijsId")
	private Long prijsId;
	@JoinColumn(name = "artikelId", referencedColumnName = "artikelId")
    @ManyToOne(optional = false)
	private Artikel artikelId;
	@JoinColumn(name = "prijsId", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
	private Prijs prijs;

	public Prijsartikel() {
	}

	public Prijsartikel(Long prijsId) {
		this.prijsId = prijsId;
	}

	public Long getPrijsId() {
		return prijsId;
	}

	public void setPrijsId(Long prijsId) {
		this.prijsId = prijsId;
	}

	public Artikel getArtikelId() {
		return artikelId;
	}

	public void setArtikelId(Artikel artikelId) {
		this.artikelId = artikelId;
	}

	public Prijs getPrijs() {
		return prijs;
	}

	public void setPrijs(Prijs prijs) {
		this.prijs = prijs;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (prijsId != null ? prijsId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Prijsartikel)) {
			return false;
		}
		Prijsartikel other = (Prijsartikel) object;
		if ((this.prijsId == null && other.prijsId != null) || (this.prijsId != null && !this.prijsId.equals(other.prijsId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Prijsartikel[ prijsId=" + prijsId + " ]";
	}
	
}
