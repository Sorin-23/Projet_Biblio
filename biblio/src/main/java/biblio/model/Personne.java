package biblio.model;

import com.fasterxml.jackson.annotation.JsonView;

import biblio.view.Views;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personne")
public class Personne {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
    private Integer id;

    @Column(nullable = false, unique = true)
    @JsonView(Views.Common.class)
    private String login;

    @Column(nullable = false)
    @JsonView(Views.Common.class)
    private String password;

    @Column(nullable = false)
    @JsonView(Views.Common.class)
    private String role; // ADMIN, AUTEUR, EDITEUR, USER

	public Personne(Integer id, String login, String password, String role) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.role = role;
	}
	public Personne() {}

	
	public Personne(String login, String password, String role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Personne [id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + "]";
	}
	
	
}
