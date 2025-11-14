package biblio.model;
import com.fasterxml.jackson.annotation.JsonView;

import biblio.view.Views;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Common.class)
    private Integer id;

    @Column(length = 50,nullable = false)
    @JsonView(Views.Common.class)
    private String titre;

    @Column
    @JsonView(Views.Common.class)
    private String resume;

    @Column
    @JsonView(Views.Common.class)
    private int annee;

    @Column
    @ManyToOne
	@JsonView(Views.Livre.class)
	@JoinColumn(name="auteur")
    private Auteur auteur;
    
    @Column
    @ManyToOne
	@JsonView(Views.Livre.class)
	@JoinColumn(name="editeur")
    private Editeur editeur;

    @Column
    @ManyToOne
	@JsonView(Views.Livre.class)
	@JoinColumn(name="collection")
    private Collection collection;

    public Livre(Integer id, String titre, String resume, int annee, Editeur editeur, Collection collection) {
        this.id = id;
        this.titre = titre;
        this.resume = resume;
        this.annee = annee;
        this.editeur = editeur;
        this.collection = collection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Editeur getEditeur() {
        return editeur;
    }

    public void setEditeur(Editeur editeur) {
        this.editeur = editeur;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Livre(String titre, String resume, int annee, Editeur editeur, Collection collection) {
        this.titre = titre;
        this.resume = resume;
        this.annee = annee;
        this.editeur = editeur;
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "Livre [id=" + id + ", titre=" + titre + ", resume=" + resume + ", annee=" + annee + ", editeur="
                + editeur + ", collection=" + collection + "]";
    }


    
    //Livre (titre, résumer, année, auteur, editeur, collection?)

    

}
