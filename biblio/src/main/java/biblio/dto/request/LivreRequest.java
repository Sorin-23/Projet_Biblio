package biblio.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LivreRequest {

	@NotBlank
	private String titre;
	
	private String resume;
	
	private int annee;

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}
	
	
}
