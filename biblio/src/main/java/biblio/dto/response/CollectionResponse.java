package biblio.dto.response;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.model.Collection;
import biblio.view.Views;

public class CollectionResponse {

	@JsonView(Views.Common.class)
	private Integer id;
	@JsonView(Views.Common.class)
	private String nom;
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
	
	public static CollectionResponse convert(Collection collection) {
		CollectionResponse resp = new CollectionResponse();
		
		resp.setId(collection.getId());
		resp.setNom(collection.getNom());
		
		return resp;
		
	}
}
