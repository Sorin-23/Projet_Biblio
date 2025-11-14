package biblio.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import biblio.model.Livre;

@DataJpaTest

public class IDAOLivreTest {
	
	@Autowired
    private IDAOLivre dao;

    @Test
    void testFindByTitre() {
        // préparer un livre en mémoire
        Livre l1 = new Livre();
        l1.setTitre("Test Livre");
        l1.setResume("Résumé du test livre");
        l1.setAnnee(2023);
        dao.save(l1);

        List<Livre> result = dao.findByTitre("Test Livre");
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTitre()).isEqualTo("Test Livre");
    }

}
