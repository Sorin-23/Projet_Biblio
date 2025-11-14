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

import biblio.model.Livre;
import biblio.service.LivreService;
import biblio.view.Views;

@RestController
@RequestMapping("/api/livre")
public class LivreRestController {

	@Autowired
	private LivreService srv;
	
	@GetMapping
	@JsonView(Views.Livre.class)
	public List<Livre> allLivres(){
		return this.srv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Livre.class)
	public Livre ficheLivre(@PathVariable Integer id) {
		return this.srv.getById(id);
	}
	
	@PostMapping
	@JsonView(Views.Livre.class)
	public Livre ajouterStagiaire(@RequestBody Livre livre)
	{
		return (Livre) this.srv.create(livre);
	}

	@PutMapping("/{id}")
	@JsonView(Views.Livre.class)
	public Livre modifierStagiaire(@PathVariable Integer id,@RequestBody Livre livre)
	{
		livre.setId(id);
		return (Livre) this.srv.update(livre);
	}

	@DeleteMapping("/{id}")
	public void deleteStagiaire(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
	}
}
