package biblio.rest;

import java.util.List;

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

import biblio.model.Auteur;
import biblio.service.AuteurService;

@WebMvcTest(AuteurRestController.class)
@EnableMethodSecurity(prePostEnabled = true)
public class AuteurRestControllerTest {
	private static final int AUTEUR_ID = 1;
    private static final String AUTEUR_NAME = "Para";
    private static final String AUTEUR_PRENOM = "chute";
    private static final String AUTEUR_NATIONALITE = "Fr";
    private static final String API_URL = "/api/AUTEUR";
    private static final String API_URL_BY_ID = API_URL + "/" + AUTEUR_ID;
    
    @MockitoBean
    private AuteurService srv;
    
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
        Auteur a1 = new Auteur();

        a1.setId(AUTEUR_ID);
        a1.setNom(AUTEUR_NAME);
        a1.setPrenom(AUTEUR_PRENOM);
        a1.setNationalite(AUTEUR_NATIONALITE);

        Mockito.when(this.srv.getAll()).thenReturn(List.of(a1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].nom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].prenom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].nationalite").exists());
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
        Mockito.when(this.srv.getById(AUTEUR_ID)).thenReturn(new Auteur());

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    //@WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(AUTEUR_ID)).thenReturn(new Auteur());

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(AUTEUR_ID);
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
        Auteur a1 = new Auteur();

        a1.setId(AUTEUR_ID);
        a1.setNom(AUTEUR_NAME);
        a1.setPrenom(AUTEUR_PRENOM);
        a1.setNationalite(AUTEUR_NATIONALITE);
        Mockito.when(this.srv.getById(AUTEUR_ID)).thenReturn(a1);

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.prenom").doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nationalite").exists());
    }
    
    
}
