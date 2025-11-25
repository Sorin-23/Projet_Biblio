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

import biblio.dto.request.LivreRequest;
import biblio.dto.response.LivreResponse;
import biblio.exception.IdNotFoundException;
import biblio.model.Livre;
import biblio.service.AuteurService;
import biblio.service.CollectionService;
import biblio.service.EditeurService;
import biblio.service.LivreService;
import biblio.view.Views;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livre")
@PreAuthorize("hasAnyRole('USER', 'EDITEUR', 'ADMIN', 'AUTEUR')")
public class LivreRestController {
	private final static Logger log = LoggerFactory.getLogger(LivreRestController.class);

	@Autowired
	private LivreService srv;

	@Autowired
	private AuteurService auteurSrv;

	@Autowired
	private EditeurService editeurSrv;

	@Autowired
	private CollectionService collectionSrv;
	
	@GetMapping
	@JsonView(Views.Livre.class)
	public List<Livre> allLivres(){
		log.info("GET /api/livre - allLivres() called");
		return this.srv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Livre.class)
	public LivreResponse ficheLivre(@PathVariable Integer id) {
		log.info("GET /api/livre/{} - ficheLivre() called", id);
		return this.srv.getById(id).map(LivreResponse::convert).orElseThrow(IdNotFoundException::new);
	}
	
	@PostMapping
	@JsonView(Views.Livre.class)
	@PreAuthorize("hasAnyRole('AUTEUR', 'EDITEUR', 'ADMIN')")
	public LivreResponse ajouterLivre(@Valid @RequestBody LivreRequest request)
	{
		log.info("POST /api/livre - ajoutLivre() called with request: {}", request);
		Livre livre = new Livre();
		BeanUtils.copyProperties(request, livre);
		livre.setAuteur(auteurSrv.getById(request.getAuteurId()).orElseThrow(IdNotFoundException::new));
		livre.setEditeur(editeurSrv.getById(request.getEditeurId()).orElseThrow(IdNotFoundException::new));
		livre.setCollection(collectionSrv.getById(request.getCollectionId()).orElseThrow(IdNotFoundException::new));
		
		this.srv.create(livre);
		
		return LivreResponse.convert(livre);
	}

	@PutMapping("/{id}")
	@JsonView(Views.Livre.class)
	@PreAuthorize("hasAnyRole('EDITEUR', 'ADMIN')")
	public LivreResponse modifierLivre(@PathVariable Integer id,@Valid @RequestBody LivreRequest request)
	{
		log.info("PUT /api/livre/{} - modifierLivre() called with filière: {}", id, request);
		Livre livre = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
		
		BeanUtils.copyProperties(request, livre);
		livre.setAuteur(auteurSrv.getById(request.getAuteurId()).orElseThrow(IdNotFoundException::new));
		livre.setEditeur(editeurSrv.getById(request.getEditeurId()).orElseThrow(IdNotFoundException::new));
		livre.setCollection(collectionSrv.getById(request.getCollectionId()).orElseThrow(IdNotFoundException::new));
		//livre.setAuteur(auteurSrv.getById(request.getAuteurId()).orElseThrow(IdNotFoundException::new));
		
		this.srv.update(livre);
		
		return LivreResponse.convert(livre);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteLivre(@PathVariable Integer id)
	{
		log.info("DELETE /api/livre/{} - supprimerLivre() called", id);
		this.srv.deleteById(id);
		this.srv.deleteById(id);
	}
}
