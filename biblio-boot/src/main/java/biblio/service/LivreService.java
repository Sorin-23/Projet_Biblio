package biblio.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOLivre;
import biblio.model.Livre;

@Service
public class LivreService {

	private static final Logger log = LoggerFactory.getLogger(LivreService.class);
	@Autowired
	IDAOLivre daoLivre;

	public Optional<Livre> getById(Integer id) {
		log.info("LivreService.getById() called with id: {}", id);
	    return daoLivre.findById(id);
	}


	public List<Livre> getAll()
	{
		log.info("LivreService.getAll() called");
		return daoLivre.findAll();
	}

	public Livre create(Livre livre) 
	{
		log.info("LivreService.create() called with livre: {}", livre);
		return daoLivre.save(livre);
	}

	public Livre update(Livre livre) 
	{
		log.info("LivreService.update() called with livre: {}", livre);
		return daoLivre.save(livre);
	}

	public void deleteById(Integer id) 
	{
		log.info("LivreService.deleteById() called with id: {}", id);
		daoLivre.deleteById(id);
	}

	public void delete(Livre livre)
	{
		log.info("LivreService.delete() called with filière: {}", livre);
		daoLivre.delete(livre);
	}
	
}
