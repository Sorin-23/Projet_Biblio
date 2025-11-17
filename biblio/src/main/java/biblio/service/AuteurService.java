package biblio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOAuteur;
import biblio.model.Auteur;

@Service
public class AuteurService {

	@Autowired
	IDAOAuteur daoAuteur;

	public Optional<Auteur> getById(Integer id) {
	    return daoAuteur.findById(id);
	}


	public List<Auteur> getAll()
	{
		return daoAuteur.findAll();
	}

	public Auteur create(Auteur auteur) 
	{
		return daoAuteur.save(auteur);
	}

	public Auteur update(Auteur auteur) 
	{
		return daoAuteur.save(auteur);
	}

	public void deleteById(Integer id) 
	{
		daoAuteur.deleteById(id);
	}

	public void delete(Auteur auteur)
	{
		daoAuteur.delete(auteur);
	}
	
}
