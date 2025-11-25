package biblio.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LivreRequest {

	@NotBlank
	private String titre;
	
	private String resume;
	
	private int annee;

	public Integer getEditeurId() {
		return editeurId;
	}

	public void setEditeurId(Integer editeurId) {
		this.editeurId = editeurId;
	}

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	private Integer auteurId;

	private Integer editeurId;

	private Integer collectionId;

	

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

	public Integer getAuteurId() {
		return auteurId;
	}

	public void setAuteurId(Integer auteurId) {
		this.auteurId = auteurId;
	}
	
	
}
