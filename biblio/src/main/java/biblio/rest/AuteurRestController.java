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

import biblio.dto.request.AuteurRequest;
import biblio.dto.response.AuteurResponse;
import biblio.exception.IdNotFoundException;
import biblio.model.Auteur;
import biblio.service.AuteurService;
import biblio.view.Views;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auteur")
@PreAuthorize("hasAnyRole('USER', 'EDITEUR', 'ADMIN', 'AUTEUR')")
public class AuteurRestController {
	private final static Logger log = LoggerFactory.getLogger(AuteurRestController.class);

	@Autowired
	private AuteurService srv;
	
	@GetMapping
	@JsonView(Views.Auteur.class)
	public List<AuteurResponse> allAuteurs(){
	log.info("GET /api/auteur - allAuteurs() called");
		return this.srv.getAll().stream().map(AuteurResponse::convert).toList();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Auteur.class)
	public AuteurResponse ficheAuteur(@PathVariable int id) {
		log.info("GET /api/auteur/{} - ficheAuteur() called", id);
	    return this.srv.getById(id).map(AuteurResponse::convert).orElseThrow(IdNotFoundException::new);
	}
	
	@PostMapping
	@JsonView(Views.Auteur.class)
	@PreAuthorize("hasAnyRole('EDITEUR', 'ADMIN')")
	public AuteurResponse ajouterAuteur(@Valid @RequestBody AuteurRequest request)
	{
		log.info("POST /api/auteur - ajoutAuteur() called with request: {}", request);
		Auteur auteur = new Auteur();
	    BeanUtils.copyProperties(request, auteur);

	    this.srv.create(auteur);

	    return AuteurResponse.convert(auteur);
	}

	@PutMapping("/{id}")
	@JsonView(Views.Auteur.class)
	@PreAuthorize("hasAnyRole('EDITEUR', 'ADMIN')")
	public AuteurResponse modifierAuteur(@PathVariable int id, @Valid @RequestBody AuteurRequest request)
	{
		log.info("PUT /api/auteur/{} - modifierAuteur() called with filière: {}", id, request);

		Auteur auteur = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
	    BeanUtils.copyProperties(request, auteur);

	    this.srv.update(auteur);

	    return AuteurResponse.convert(auteur);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteAuteur(@PathVariable Integer id)
	{
		log.info("DELETE /api/auteur/{} - supprimerAuteur() called", id);
		this.srv.deleteById(id);
	}
}
