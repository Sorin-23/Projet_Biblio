package biblio.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import biblio.model.Auteur;
import biblio.model.Collection;
import biblio.model.Editeur;
import biblio.model.Livre;

@DataJpaTest

public class IDAOLivreTest {
	
	@Autowired
    private IDAOLivre daoLivre;
	@Autowired
    private IDAOAuteur daoAuteur;
	@Autowired
    private IDAOCollection daoCollection;
	@Autowired
    private IDAOEditeur daoEditeur;
	
	private Auteur auteur;
    private Editeur editeur;
    private Collection collection;
    private Livre livre;

    @Test
    void testFindByTitre() {
        Livre l1 = new Livre();
        l1.setTitre("Test Livre");
        l1.setResume("Résumé du test livre");
        l1.setAnnee(2023);
        daoLivre.save(l1);

        List<Livre> result = daoLivre.findByTitre("Test Livre");
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTitre()).isEqualTo("Test Livre");
    }
    
    @Test
    void testFindByAuteur() {
    	
    	auteur = new Auteur("Dupont", "Jean", "FR");
        editeur = new Editeur("Gallimard", "France");
        collection = new Collection("Classiques");
        daoAuteur.save(auteur);
        daoEditeur.save(editeur);
        daoCollection.save(collection);
        
        livre = new Livre("Test Livre","Résumé du test livre",2023,auteur,editeur,collection); 
    	
        daoLivre.save(livre);
        
    	List<Livre> result = daoLivre.findByAuteur(auteur);
    	assertThat(result).isNotEmpty();
        assertThat(result.get(0).getAuteur().getNom()).isEqualTo("Dupont");
    	
    }
    
    @Test
    void testFindByEditeur() {
    	
    	auteur = new Auteur("Dupont", "Jean", "FR");
        editeur = new Editeur("Gallimard", "France");
        collection = new Collection("Classiques");
        daoAuteur.save(auteur);
        daoEditeur.save(editeur);
        daoCollection.save(collection);
        
        livre = new Livre("Test Livre","Résumé du test livre",2023,auteur,editeur,collection); 
    	
        daoLivre.save(livre);
        
    	List<Livre> result = daoLivre.findByEditeur(editeur);
    	assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEditeur().getNom()).isEqualTo("Gallimard");
    	
    }
    
    @Test
    void testFindByCollection() {
    	
    	auteur = new Auteur("Dupont", "Jean", "FR");
        editeur = new Editeur("Gallimard", "France");
        collection = new Collection("Classiques");
        daoAuteur.save(auteur);
        daoEditeur.save(editeur);
        daoCollection.save(collection);
        
        livre = new Livre("Test Livre","Résumé du test livre",2023,auteur,editeur,collection); 
    	
        daoLivre.save(livre);
        
    	List<Livre> result = daoLivre.findByCollection(collection);
    	assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCollection().getNom()).isEqualTo("Classiques");
    	
    	
    }

}
