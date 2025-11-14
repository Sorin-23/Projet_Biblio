package biblio.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import biblio.dao.IDAOAuteur;

@ExtendWith(MockitoExtension.class)

public class AuteurServiceTest {
	
	@Mock
    private IDAOAuteur dao;

    @InjectMocks
    private AuteurService service;

}
