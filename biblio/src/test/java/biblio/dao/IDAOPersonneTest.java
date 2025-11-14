package biblio.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import biblio.model.Personne;

@DataJpaTest
public class IDAOPersonneTest {
	
	@Autowired
	private IDAOPersonne dao;
	
	

	
	/*@Test
	void testFindByLoginAndPassword() {
		Personne p = new Personne();
        p.setLogin("jdoe");
        p.setPassword("password123");
        p.setRole("AUTEUR");
        dao.save(p);
        
        Personne pers  = dao.findByLoginAndPassword("jdoe", "password123");
        assertThat(pers).isNotNull();
        assertThat(pers.getRole()).isEqualTo("AUTEUR");
        assertThat(pers.getRole()).isNotEqualTo("EDITEUR");
		
	}*/
	
	@Test 
	void testFindByLogin() {
		Personne p = new Personne();
        p.setLogin("jdoe");
        p.setPassword("password123");
        p.setRole("AUTEUR");
        dao.save(p);

        Optional<Personne> pers = dao.findByLogin("jdoe");
        assertThat(pers).isPresent();
        assertThat(pers.get().getLogin()).isEqualTo("jdoe");
        assertThat(pers.get().getRole()).isEqualTo("AUTEUR");
	}
}
