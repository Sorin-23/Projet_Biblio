package biblio.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class AuteurRequest {
	
	@NotBlank
	private String nom;
	
	@NotBlank
	private String prenom;

	/*private List<LivreRequest> livres;
	
	public List<LivreRequest> getLivres() {
		return livres;
	}

	public void setLivres(List<LivreRequest> livres) {
		this.livres = livres;
	}*/

	private String nationalite;

	

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
	
	
	
	

}
