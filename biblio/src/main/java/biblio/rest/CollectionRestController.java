package biblio.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.model.Collection;
import biblio.service.CollectionService;
import biblio.view.Views;


@RestController
@RequestMapping("/api/collection")
public class CollectionRestController {
	
	@Autowired
	private CollectionService srv;
	
	@GetMapping
	@JsonView(Views.Collection.class)
	public List<Collection> allCollections(){
		return this.srv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Collection.class)
	public Collection ficheCollection(@PathVariable Integer id) {
		return this.srv.getById(id);
	}
	
	@PostMapping
	@JsonView(Views.Collection.class)
	public Collection ajouterStagiaire(@RequestBody Collection collection)
	{
		return (Collection) this.srv.create(collection);
	}

	@PutMapping("/{id}")
	@JsonView(Views.Collection.class)
	public Collection modifierStagiaire(@PathVariable Integer id,@RequestBody Collection collection)
	{
		collection.setId(id);
		return (Collection) this.srv.update(collection);
	}

	@DeleteMapping("/{id}")
	public void deleteStagiaire(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
	}
	

}
