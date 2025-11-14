package biblio.rest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import biblio.model.Livre;
import biblio.service.LivreService;


@WebMvcTest(LivreRestController.class)
@EnableMethodSecurity(prePostEnabled = true)
public class LivreRestControllerTest {

	private static final int LIVRE_ID = 1;
    private static final String LIVRE_NAME = "Parachute";
    private static final String LIVRE_RESUME = "Parachute";
    private static final int LIVRE_ANNEE = 2000;
    private static final String API_URL = "/api/livre";
    private static final String API_URL_BY_ID = API_URL + "/" + LIVRE_ID;
    
    @MockitoBean
    private LivreService srv;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldFindAllStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
    
    @Test
    //@WithMockUser
    void shouldFindAllStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    //@WithMockUser
    void shouldGetAllUseServiceGetAll() throws Exception {
        // given

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        Mockito.verify(this.srv).getAll();
    }
    
    @Test
    //@WithMockUser
    void shouldFindAllReturnAttributes() throws Exception {
        // given
        Livre l1 = new Livre();

        l1.setId(LIVRE_ID);
        l1.setTitre(LIVRE_NAME);
        l1.setResume(LIVRE_RESUME);
        l1.setAnnee(LIVRE_ANNEE);

        Mockito.when(this.srv.getAll()).thenReturn(List.of(l1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].titre").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].resume").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].annee").exists());
    }
    
    @Test
    void shouldGetdByIdStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    //@WithMockUser
    void shouldGetByIdStatusOk() throws Exception {
        // given
        Mockito.when(this.srv.getById(LIVRE_ID)).thenReturn(new Livre());

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    //@WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(LIVRE_ID)).thenReturn(new Livre());

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(LIVRE_ID);
    }

    @Test
    //@WithMockUser
    void shouldGetByIdStatusNotFoundWhenIdNotFound() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    //@WithMockUser
    void shouldGetByIdReturnAttributes() throws Exception {
        // given
        Livre l1 = new Livre();

        l1.setId(LIVRE_ID);
        l1.setTitre(LIVRE_NAME);
        l1.setResume(LIVRE_RESUME);
        l1.setAnnee(LIVRE_ANNEE);
        Mockito.when(this.srv.getById(LIVRE_ID)).thenReturn(l1);

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.titre").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.resume").doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.annee").exists());
    }
    
    
}

