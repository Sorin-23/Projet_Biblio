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

import biblio.model.Auteur;
import biblio.service.AuteurService;
import biblio.view.Views;

@RestController
@RequestMapping("/api/auteur")
public class AuteurRestController {

	@Autowired
	private AuteurService srv;
	
	@GetMapping
	@JsonView(Views.Auteur.class)
	public List<Auteur> allAuteurs(){
		return this.srv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Auteur.class)
	public Auteur ficheAuteur(@PathVariable Integer id) {
		return this.srv.getById(id);
	}
	
	@PostMapping
	@JsonView(Views.Auteur.class)
	public Auteur ajouterStagiaire(@RequestBody Auteur auteur)
	{
		return (Auteur) this.srv.create(auteur);
	}

	@PutMapping("/{id}")
	@JsonView(Views.Auteur.class)
	public Auteur modifierStagiaire(@PathVariable Integer id,@RequestBody Auteur auteur)
	{
		auteur.setId(id);
		return (Auteur) this.srv.update(auteur);
	}

	@DeleteMapping("/{id}")
	public void deleteStagiaire(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
	}
}
