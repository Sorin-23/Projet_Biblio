package biblio.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.dto.request.LivreRequest;
import biblio.model.Auteur;
import biblio.view.Views;

public class AuteurResponse {
	
	@JsonView(Views.Common.class)
	private Integer id;
	@JsonView(Views.Common.class)
	private String nom;
	@JsonView(Views.Common.class)
	private String prenom;
	@JsonView(Views.Common.class)
	private String nationalite;
	/*@JsonView(Views.auteurWithLivres.class)
	private List<LivreResponse> livres;
	
	public List<LivreResponse> getLivres() {
		return livres;
	}

	public void setLivres(List<LivreResponse> livres) {
		this.livres = livres;
	}*/
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNationalite() {
		return nationalite;
	}
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	
	public static AuteurResponse convert(Auteur auteur) {
		AuteurResponse resp = new AuteurResponse();
		
		resp.setId(auteur.getId());
		resp.setNom(auteur.getNom());
		resp.setPrenom(auteur.getPrenom());
		resp.setNationalite(auteur.getNationalite());
		//resp.setLivres(auteur.getLivres().stream().map(LivreResponse::convert).toList());
		
		return resp;
		
	}
	

}
