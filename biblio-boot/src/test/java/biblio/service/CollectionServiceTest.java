package biblio.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import biblio.dao.IDAOCollection;

@ExtendWith(MockitoExtension.class)

public class CollectionServiceTest {
	
	@Mock
    private IDAOCollection dao;

    @InjectMocks
    private CollectionService service;


}
