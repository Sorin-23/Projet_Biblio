package biblio.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import biblio.model.Personne;

public interface IDAOPersonne extends JpaRepository<Personne,Integer>{
	//public Personne findByLoginAndPassword(String login,String password);

	public Optional<Personne> findByLogin(String login);
}
