package biblio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOLivre;
import biblio.model.Livre;

@Service
public class LivreService {

	@Autowired
	IDAOLivre daoLivre;

	public Optional<Livre> getById(Integer id) {
	    return daoLivre.findById(id);
	}


	public List<Livre> getAll()
	{
		return daoLivre.findAll();
	}

	public Livre create(Livre livre) 
	{
		return daoLivre.save(livre);
	}

	public Livre update(Livre livre) 
	{
		return daoLivre.save(livre);
	}

	public void deleteById(Integer id) 
	{
		daoLivre.deleteById(id);
	}

	public void delete(Livre livre)
	{
		daoLivre.delete(livre);
	}
	
}
