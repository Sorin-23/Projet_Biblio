package biblio.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import biblio.dao.IDAOEditeur;

@ExtendWith(MockitoExtension.class)

public class EditeurServiceTest {

	@Mock
    private IDAOEditeur dao;

    @InjectMocks
    private EditeurService service;

}
