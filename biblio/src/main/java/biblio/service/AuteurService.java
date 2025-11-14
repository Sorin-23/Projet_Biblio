package biblio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOAuteur;
import biblio.model.Auteur;

@Service
public class AuteurService {

	@Autowired
	IDAOAuteur daoAuteur;

	public Auteur getById(Integer id) {
	    return daoAuteur.findById(id).orElse(null);
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
