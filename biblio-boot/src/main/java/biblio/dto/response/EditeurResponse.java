package biblio.dto.response;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.model.Editeur;
import biblio.view.Views;

public class EditeurResponse {

	@JsonView(Views.Common.class)
	private Integer id;
	@JsonView(Views.Common.class)
	private String nom;
	@JsonView(Views.Common.class)
	private String pays;
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
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	
	public static EditeurResponse convert(Editeur editeur) {
		EditeurResponse resp = new EditeurResponse();
		
		resp.setId(editeur.getId());
		resp.setNom(editeur.getNom());
		resp.setPays(editeur.getPays());
		
		return resp;
		
	}
	
	
}
