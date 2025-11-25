package biblio.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOCollection;
import biblio.model.Collection;

@Service
public class CollectionService {
	private static final Logger log = LoggerFactory.getLogger(CollectionService.class);

	@Autowired
	IDAOCollection daoCollection;

	public Optional<Collection> getById(Integer id) {
		log.info("CollectionService.getById() called with id: {}", id);
	    return daoCollection.findById(id);
	}


	public List<Collection> getAll()
	{
		log.info("CollectionService.getAll() called");
		return daoCollection.findAll();
	}

	public Collection create(Collection collection) 
	{
		log.info("CollectionService.create() called with filière: {}", collection);
		return daoCollection.save(collection);
	}

	public Collection update(Collection collection) 
	{
		log.info("CollectionService.update() called with filière: {}", collection);
		return daoCollection.save(collection);
	}

	public void deleteById(Integer id) 
	{
		log.info("CollectionService.deleteById() called with id: {}", id);
		daoCollection.deleteById(id);
	}

	public void delete(Collection collection)
	{
		log.info("CollectionService.delete() called with collection: {}", collection);
		daoCollection.delete(collection);
	}
	
}
