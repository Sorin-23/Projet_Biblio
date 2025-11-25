package biblio.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOEditeur;
import biblio.model.Editeur;

@Service
public class EditeurService {
	private static final Logger log = LoggerFactory.getLogger(EditeurService.class);

	@Autowired
	IDAOEditeur daoEditeur;

	public Optional<Editeur> getById(Integer id) {
		log.info("EditeurService.getById() called with id: {}", id);

	    return daoEditeur.findById(id);
	}


	public List<Editeur> getAll()
	{
		log.info("EditeurService.getAll() called");
		return daoEditeur.findAll();
	}

	public Editeur create(Editeur editeur) 
	{
		log.info("EditeurService.create() called with collection: {}", editeur);
		return daoEditeur.save(editeur);
	}

	public Editeur update(Editeur editeur) 
	{
		log.info("EditeurService.update() called with filière: {}", editeur);
		return daoEditeur.save(editeur);
	}

	public void deleteById(Integer id) 
	{
		log.info("EditeurService.deleteById() called with id: {}", id);
		daoEditeur.deleteById(id);
	}

	public void delete(Editeur editeur)
	{
		log.info("EditeurService.delete() called with filière: {}", editeur);
		daoEditeur.delete(editeur);
	}
	
}
