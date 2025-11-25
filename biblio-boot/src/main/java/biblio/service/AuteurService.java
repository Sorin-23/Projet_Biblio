package biblio.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOAuteur;
import biblio.model.Auteur;

@Service
public class AuteurService {
	private static final Logger log = LoggerFactory.getLogger(AuteurService.class);

	@Autowired
	IDAOAuteur daoAuteur;

	public Optional<Auteur> getById(Integer id) {
		log.info("AuteurService.getById() called with id: {}", id);
	    return daoAuteur.findById(id);
	}


	public List<Auteur> getAll()
	{
		log.info("AuteurService.getAll() called");
		return daoAuteur.findAll();
	}

	public Auteur create(Auteur auteur) 
	{
		log.info("AuteurService.create() called with auteur: {}", auteur);
		return daoAuteur.save(auteur);
	}

	public Auteur update(Auteur auteur) 
	{
		log.info("AuteurService.update() called with Auteur: {}", auteur);
		return daoAuteur.save(auteur);
	}

	public void deleteById(Integer id) 
	{
		log.info("AuteurService.deleteById() called with id: {}", id);
		daoAuteur.deleteById(id);
	}

	public void delete(Auteur auteur)
	{
		log.info("AuteurService.delete() called with filière: {}", auteur);
		daoAuteur.delete(auteur);
	}
	
}
