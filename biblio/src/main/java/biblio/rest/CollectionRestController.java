package biblio.rest;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.dto.request.CollectionRequest;
import biblio.dto.response.CollectionResponse;
import biblio.exception.IdNotFoundException;
import biblio.model.Collection;
import biblio.service.CollectionService;
import biblio.view.Views;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/collection")
@PreAuthorize("hasAnyRole('USER', 'EDITEUR', 'ADMIN', 'AUTEUR')")
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
	public CollectionResponse ficheCollection(@PathVariable Integer id) {
		return this.srv.getById(id).map(CollectionResponse::convert).orElseThrow(IdNotFoundException::new);
	}
	
	@PostMapping
	@JsonView(Views.Collection.class)
	@PreAuthorize("hasAnyRole('EDITEUR', 'ADMIN')")
	public CollectionResponse ajouterCollection(@Valid @RequestBody CollectionRequest request)
	{
		Collection collection = new Collection();
	    BeanUtils.copyProperties(request, collection);

	    this.srv.create(collection);

	    return CollectionResponse.convert(collection);
	}

	@PutMapping("/{id}")
	@JsonView(Views.Collection.class)
	@PreAuthorize("hasAnyRole('EDITEUR', 'ADMIN')")
	public CollectionResponse modifierCollection(@PathVariable Integer id,@Valid @RequestBody CollectionRequest request)
	{
		Collection collection = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
	    BeanUtils.copyProperties(request, collection);

	    this.srv.update(collection);

	    return CollectionResponse.convert(collection);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteCollection(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
	}
	

}
