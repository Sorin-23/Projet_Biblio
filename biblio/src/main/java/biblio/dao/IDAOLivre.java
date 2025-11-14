package biblio.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import biblio.model.Livre;

public interface IDAOLivre extends JpaRepository<Livre,Integer>{

	public List<Livre> findByTitre(String nom);
}
