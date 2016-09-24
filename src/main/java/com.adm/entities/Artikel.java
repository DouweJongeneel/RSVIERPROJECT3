/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import com.adm.web.controllers.SessionController;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "artikel")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Artikel.findAll", query = "SELECT a FROM Artikel a"),
	@NamedQuery(name = "Artikel.findByArtikelId", query = "SELECT a FROM Artikel a WHERE a.artikelId = :artikelId"),
	@NamedQuery(name = "Artikel.findByArtikelNaam", query = "SELECT a FROM Artikel a WHERE a.artikelNaam = :artikelNaam"),
	@NamedQuery(name = "Artikel.findByArtikelType", query = "SELECT a FROM Artikel a WHERE a.artikelType = :artikelType"),
	@NamedQuery(name = "Artikel.findByDatumAanmaak", query = "SELECT a FROM Artikel a WHERE a.datumAanmaak = :datumAanmaak"),
	@NamedQuery(name = "Artikel.findByInAssortiment", query = "SELECT a FROM Artikel a WHERE a.inAssortiment = :inAssortiment"),
	@NamedQuery(name = "Artikel.findByVerwachteLevertijd", query = "SELECT a FROM Artikel a WHERE a.verwachteLevertijd = :verwachteLevertijd")})
public class Artikel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Basic(optional = false)
    @Column(name = "artikelId")
	@GeneratedValue(strategy = IDENTITY)
	private Long artikelId;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "artikelNaam")
	private String artikelNaam;

	@Size(max = 255)
    @Column(name = "artikelType")
	private String artikelType;

	@Basic(optional = false)
    @NotNull
    @Column(name = "datumAanmaak")
    @Temporal(TemporalType.TIMESTAMP)
	private Date datumAanmaak;

	@Basic(optional = false)
    @NotNull
    @Column(name = "inAssortiment")
	private boolean inAssortiment;

	@Column(name = "verwachteLevertijd")
	private Integer verwachteLevertijd;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "artikelId")
	private Collection<Bestelartikel> bestelartikelCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="artikel")
	private Collection<Prijs> prijsCollection;
	
	public Artikel() {
	}

	public Artikel(Long artikelId) {
		this.artikelId = artikelId;
	}

	public Artikel(String artikelNaam, Integer verwachteLevertijd, String artikelType) {
		this.artikelNaam = artikelNaam;
		this.verwachteLevertijd = verwachteLevertijd;
		this.artikelType = artikelType;
		inAssortiment = true;
		datumAanmaak = new Date(System.currentTimeMillis());
	}
	
	public Artikel(String artikelNaam, Integer verwachteLevertijd, boolean opVoorraad) {
		this.artikelNaam = artikelNaam;
		this.verwachteLevertijd = verwachteLevertijd;
		inAssortiment = opVoorraad;
		datumAanmaak = new Date(System.currentTimeMillis());
	}

	public Long getArtikelId() {
		return artikelId;
	}

	public void setArtikelId(Long artikelId) {
		this.artikelId = artikelId;
	}

	public String getArtikelNaam() {
		return artikelNaam;
	}

	public void setArtikelNaam(String artikelNaam) {
		this.artikelNaam = artikelNaam;
	}

	public String getArtikelType() {
		return artikelType;
	}

	public void setArtikelType(String artikelType) {
		this.artikelType = artikelType;
	}

	public Date getDatumAanmaak() {
		return datumAanmaak;
	}

	public void setDatumAanmaak(Date datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}

	public boolean getInAssortiment() {
		return inAssortiment;
	}

	public void setInAssortiment(boolean inAssortiment) {
		this.inAssortiment = inAssortiment;
	}

	public Integer getVerwachteLevertijd() {
		return verwachteLevertijd;
	}

	public void setVerwachteLevertijd(Integer verwachteLevertijd) {
		this.verwachteLevertijd = verwachteLevertijd;
	}

	@XmlTransient
	public Collection<Bestelartikel> getBestelartikelCollection() {
		return bestelartikelCollection;
	}

	public void setBestelartikelCollection(Collection<Bestelartikel> bestelartikelCollection) {
		this.bestelartikelCollection = bestelartikelCollection;
	}

	@XmlTransient
	public Collection<Prijs> getPrijsCollection() {
		return prijsCollection;
	}

	public void setPrijsCollection(Collection<Prijs> prijsCollection) {
		this.prijsCollection = prijsCollection;
	}

	public Prijs getActuelePrijs() {
		return ((Prijs)prijsCollection.toArray()[prijsCollection.size()-1]);
	}

	public byte[] getImage() {
		File image = new File(SessionController.getBasePath() + "JEE/artikel/" + artikelId + ".jpg");

		try {
			return Files.readAllBytes(image.toPath());
		} catch (IOException ex) {
			Logger.getLogger(Artikel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public void addPrijsAanCollectie(Prijs prijs){
		if(prijsCollection == null){
			prijsCollection = new ArrayList<Prijs>();
		}
		prijsCollection.add(prijs);
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (artikelId != null ? artikelId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Artikel)) {
			return false;
		}
		Artikel other = (Artikel) object;
		if ((this.artikelId == null && other.artikelId != null) || (this.artikelId != null && !this.artikelId.equals(other.artikelId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return artikelNaam + " " + getActuelePrijs().toString();
	}
	
}
