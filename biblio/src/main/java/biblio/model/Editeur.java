package biblio.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.view.Views;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="editeur")
public class Editeur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	@JsonView(Views.Common.class)
	private String nom;
	
	@Column(nullable = false, length = 100)
	@JsonView(Views.Common.class)
	private String pays;
	
	@OneToMany(mappedBy="livre")
	@JsonView(Views.editeurWithLivres.class)
	private List<Livre> livres;
	
	
	public Editeur() {
	}

	public Editeur(Integer id, String nom, String pays) {
		this.id = id;
		this.nom = nom;
		this.pays = pays;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	@Override
	public String toString() {
		return "Editeur [id=" + id + ", nom=" + nom + ", pays=" + pays + "]";
	}
	
	
	
}
