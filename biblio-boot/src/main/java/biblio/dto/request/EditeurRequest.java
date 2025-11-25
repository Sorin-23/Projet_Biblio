package biblio.dto.request;

import jakarta.validation.constraints.NotBlank;

public class EditeurRequest {

	@NotBlank
	private String nom;
	
	@NotBlank
	private String pays;
	
	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
