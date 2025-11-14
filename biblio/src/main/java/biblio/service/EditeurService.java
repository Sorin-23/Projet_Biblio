package biblio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOEditeur;
import biblio.model.Editeur;

@Service
public class EditeurService {

	@Autowired
	IDAOEditeur daoEditeur;

	public Editeur getById(Integer id) {
	    return daoEditeur.findById(id).orElse(null);
	}


	public List<Editeur> getAll()
	{
		return daoEditeur.findAll();
	}

	public Editeur create(Editeur editeur) 
	{
		return daoEditeur.save(editeur);
	}

	public Editeur update(Editeur editeur) 
	{
		return daoEditeur.save(editeur);
	}

	public void deleteById(Integer id) 
	{
		daoEditeur.deleteById(id);
	}

	public void delete(Editeur editeur)
	{
		daoEditeur.delete(editeur);
	}
	
}
