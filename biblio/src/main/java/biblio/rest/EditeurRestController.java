package biblio.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import biblio.dto.request.EditeurRequest;
import biblio.dto.response.EditeurResponse;
import biblio.exception.IdNotFoundException;
import biblio.model.Editeur;
import biblio.service.EditeurService;
import biblio.view.Views;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/editeur")
@PreAuthorize("hasAnyRole('USER', 'EDITEUR', 'ADMIN', 'AUTEUR')")
public class EditeurRestController {
	private final static Logger log = LoggerFactory.getLogger(EditeurRestController.class);

	
	@Autowired
	private EditeurService srv;
	
	@GetMapping
	@JsonView(Views.Editeur.class)
	public List<Editeur> allEditeurs(){
		log.info("GET /api/editeur - allEditeurs() called");
		return this.srv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Editeur.class)
	public EditeurResponse ficheEditeur(@PathVariable Integer id) {
		log.info("GET /api/editeur/{} - ficheEditeur() called", id);
		return this.srv.getById(id).map(EditeurResponse::convert).orElseThrow(IdNotFoundException::new);
	}
	
	@PostMapping
	@JsonView(Views.Editeur.class)
	@PreAuthorize("hasAnyRole('EDITEUR', 'ADMIN')")
	public EditeurResponse ajouterEditeur(@Valid @RequestBody EditeurRequest request)
	{
		log.info("POST /api/editeur - ajoutEditeur() called with request: {}", request);
		Editeur editeur = new Editeur();
		
		BeanUtils.copyProperties(request,editeur);
		
		this.srv.create(editeur);
		
		return EditeurResponse.convert(editeur);
	}

	@PutMapping("/{id}")
	@JsonView(Views.Editeur.class)
	@PreAuthorize("hasAnyRole('EDITEUR', 'ADMIN')")
	public EditeurResponse modifierEditeur(@PathVariable Integer id,@Valid @RequestBody EditeurRequest request)
	{
		log.info("PUT /api/editeur/{} - modifierEditeur() called with filière: {}", id, request);
		Editeur editeur = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
		
		BeanUtils.copyProperties(request,editeur);
		
		this.srv.update(editeur);
		
		return EditeurResponse.convert(editeur);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteEditeur(@PathVariable Integer id)
	{
		log.info("DELETE /api/editeur/{} - supprimerEditeur() called", id);
		this.srv.deleteById(id);
	}
	

}
