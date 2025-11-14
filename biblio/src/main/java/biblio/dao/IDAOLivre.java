package biblio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import biblio.model.Livre;

public interface IDAOLivre extends JpaRepository<Livre,Integer>{

}
