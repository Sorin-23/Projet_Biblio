package biblio.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import biblio.dao.IDAOLivre;

@ExtendWith(MockitoExtension.class)

public class LivreServiceTest {
	
	@Mock
    private IDAOLivre dao;

    @InjectMocks
    private LivreService service;


}
