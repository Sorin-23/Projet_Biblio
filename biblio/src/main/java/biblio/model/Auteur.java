package biblio.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.view.Views;
import jakarta.persistence.*;

@Entity
@Table(name = "auteur")
public class Auteur {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(Views.Common.class)
        private Integer id;

        @Column(name = "nom", nullable = false)
        @JsonView(Views.Common.class)
        private String nom;

        @Column(name = "prenom", nullable = false)
        @JsonView(Views.Common.class)
        private String prenom;

        @Column(name = "nationalite", nullable = false)
        @JsonView(Views.Common.class)
        private String nationalite;
        
        @OneToMany(mappedBy="auteur")
    	@JsonView(Views.auteurWithLivres.class)
    	private List<Livre> livres;

        public Auteur() {
        }
        
        public Auteur(String nom, String prenom, String nationalite) {
            this.nom = nom;
            this.prenom = prenom;
            this.nationalite = nationalite;

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
    
        public String getPrenom() { 
            return prenom;
         }
        public void setPrenom(String prenom) { 
            this.prenom = prenom; 
        }
    
        public String getNationalite() { 
            return nationalite;
         }
        public void setNationalite(String nationalite) {
             this.nationalite = nationalite; 
            }

}

