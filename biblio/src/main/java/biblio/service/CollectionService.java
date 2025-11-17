package biblio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOCollection;
import biblio.model.Collection;

@Service
public class CollectionService {

	@Autowired
	IDAOCollection daoCollection;

	public Optional<Collection> getById(Integer id) {
	    return daoCollection.findById(id);
	}


	public List<Collection> getAll()
	{
		return daoCollection.findAll();
	}

	public Collection create(Collection collection) 
	{
		return daoCollection.save(collection);
	}

	public Collection update(Collection collection) 
	{
		return daoCollection.save(collection);
	}

	public void deleteById(Integer id) 
	{
		daoCollection.deleteById(id);
	}

	public void delete(Collection collection)
	{
		daoCollection.delete(collection);
	}
	
}
