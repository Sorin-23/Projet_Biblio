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
@Table(name="collection")
public class Collection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Common.class)

	private Integer id;
	
	@Column(nullable = false, length = 200)
    @JsonView(Views.Common.class)

	private String nom;
	
	@OneToMany(mappedBy="collection")
	@JsonView(Views.collectionWithLivres.class)
	private List<Livre> livres;
	
	
	
	public Collection() {}

	public Collection(Integer id, String nom) {
		this.id = id;
		this.nom = nom;
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

	@Override
	public String toString() {
		return "Collection [id=" + id + ", nom=" + nom + "]";
	}
	

}
