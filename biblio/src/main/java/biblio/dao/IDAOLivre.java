package biblio.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import biblio.model.Auteur;
import biblio.model.Collection;
import biblio.model.Editeur;
import biblio.model.Livre;

public interface IDAOLivre extends JpaRepository<Livre,Integer>{

	public List<Livre> findByTitre(String nom);
	
	// Par auteur
	public List<Livre> findByAuteur(Auteur auteur);

    // Par éditeur
	public List<Livre> findByEditeur(Editeur editeur);

    // Par collection
	public List<Livre> findByCollection(Collection collection);
}
