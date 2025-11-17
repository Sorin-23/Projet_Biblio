package biblio.dto.response;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.model.Livre;
import biblio.view.Views;

public class LivreResponse {
	
    @JsonView(Views.Common.class)
    private Integer id;

    @JsonView(Views.Common.class)
    private String titre;

    @JsonView(Views.Common.class)
    private String resume;
    
    @JsonView(Views.Common.class)
    private int annee;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
    
	public static LivreResponse convert(Livre livre) {
		LivreResponse resp = new LivreResponse();
		
		resp.setId(livre.getId());
		resp.setTitre(livre.getTitre());
		resp.setResume(livre.getResume());
		resp.setAnnee(livre.getAnnee());
		
		return resp;
		
	}
}
