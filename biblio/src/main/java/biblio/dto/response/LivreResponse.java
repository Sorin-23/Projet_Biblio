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

	@JsonView(Views.Livre.class)
	private AuteurResponse auteur;

	@JsonView(Views.Livre.class)
	private EditeurResponse editeur;

	@JsonView(Views.Livre.class)
	private CollectionResponse collection;

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
	
    
	public AuteurResponse getAuteur() {
		return auteur;
	}

	public void setAuteur(AuteurResponse auteur) {
		this.auteur = auteur;
	}

	public EditeurResponse getEditeur() {
		return editeur;
	}

	public void setEditeur(EditeurResponse editeur) {
		this.editeur = editeur;
	}

	public CollectionResponse getCollection() {
		return collection;
	}

	public void setCollection(CollectionResponse collection) {
		this.collection = collection;
	}

	public static LivreResponse convert(Livre livre) {
		LivreResponse resp = new LivreResponse();
		
		resp.setId(livre.getId());
		resp.setTitre(livre.getTitre());
		resp.setResume(livre.getResume());
		resp.setAnnee(livre.getAnnee());
		resp.setAuteur(AuteurResponse.convert(livre.getAuteur()));
		resp.setCollection(CollectionResponse.convert(livre.getCollection()));
		resp.setEditeur(EditeurResponse.convert(livre.getEditeur()));
		
		return resp;
		
	}
}
