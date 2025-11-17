package biblio.rest;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import biblio.config.JwtHeaderFilter;
import biblio.model.Editeur;
import biblio.service.EditeurService;

@WebMvcTest(controllers = EditeurRestController.class, excludeFilters = {
	    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtHeaderFilter.class)})
@EnableMethodSecurity(prePostEnabled = true)
public class EditeurRestControllerTest {
	private static final int EDITEUR_ID = 1;
    private static final String EDITEUR_NAME = "Gallimard";
    private static final String EDITEUR_PAYS = "France";
    private static final String API_URL = "/api/editeur";
    private static final String API_URL_BY_ID = API_URL + "/" + EDITEUR_ID;
    
    @MockitoBean
    private EditeurService srv;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldGetAllStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
    
    @Test
    @WithMockUser
    void shouldGetAllStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetAllUseServiceGetAll() throws Exception {
        // given

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        Mockito.verify(this.srv).getAll();
    }
    
    @Test
    @WithMockUser
    void shouldFindAllReturnAttributes() throws Exception {
        // given
        Editeur e1 = new Editeur();

        e1.setId(EDITEUR_ID);
        e1.setNom(EDITEUR_NAME);
        e1.setPays(EDITEUR_PAYS);
        

        Mockito.when(this.srv.getAll()).thenReturn(List.of(e1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].nom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].pays").exists());
     
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
    @WithMockUser
    void shouldGetByIdStatusOk() throws Exception {
        // given
        Mockito.when(this.srv.getById(EDITEUR_ID)).thenReturn(Optional.of(new Editeur()));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(EDITEUR_ID)).thenReturn(Optional.of(new Editeur()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(EDITEUR_ID);
    }

    @Test
    @WithMockUser
    void shouldGetByIdStatusNotFoundWhenIdNotFound() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldGetByIdReturnAttributes() throws Exception {
        // given
        Editeur e1 = new Editeur();

        e1.setId(EDITEUR_ID);
        e1.setNom(EDITEUR_NAME);
        e1.setPays(EDITEUR_PAYS);
     
        Mockito.when(this.srv.getById(EDITEUR_ID)).thenReturn(Optional.of(e1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.pays").exists());
    }
    
    
    

}
