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

import biblio.model.Editeur;
import biblio.service.EditeurService;
import biblio.view.Views;

@RestController
@RequestMapping("/api/editeur")
public class EditeurRestController {
	
	@Autowired
	private EditeurService srv;
	
	@GetMapping
	@JsonView(Views.Editeur.class)
	public List<Editeur> allEditeurs(){
		return this.srv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Editeur.class)
	public Editeur ficheEditeur(@PathVariable Integer id) {
		return this.srv.getById(id);
	}
	
	@PostMapping
	@JsonView(Views.Editeur.class)
	public Editeur ajouterStagiaire(@RequestBody Editeur editeur)
	{
		return (Editeur) this.srv.create(editeur);
	}

	@PutMapping("/{id}")
	@JsonView(Views.Editeur.class)
	public Editeur modifierStagiaire(@PathVariable Integer id,@RequestBody Editeur editeur)
	{
		editeur.setId(id);
		return (Editeur) this.srv.update(editeur);
	}

	@DeleteMapping("/{id}")
	public void deleteStagiaire(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
	}
	

}
