package biblio.rest;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import biblio.config.JwtHeaderFilter;
import biblio.dto.request.EditeurRequest;
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
    
    
    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(EDITEUR_NAME, EDITEUR_PAYS);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(EDITEUR_NAME, EDITEUR_PAYS);

        // then
        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(EDITEUR_NAME, EDITEUR_PAYS);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<Editeur> editeurCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPost(EDITEUR_NAME, EDITEUR_PAYS);

        // then
        Mockito.verify(this.srv).create(editeurCaptor.capture());

        Editeur editeur = editeurCaptor.getValue();

        Assertions.assertEquals(EDITEUR_NAME, editeur.getNom());
        Assertions.assertEquals(EDITEUR_PAYS, editeur.getPays());
       
    }

    @ParameterizedTest
    @CsvSource({
        "'',pays",
        "'  ', pays",
        ",pays",
        "nom,",
        "nom,''",
        "nom, '     '"
    })
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateStatusBadRequest(String nom, String pays) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(nom, pays);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String nom, String pays) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        EditeurRequest request = new EditeurRequest();

        request.setNom(nom);
        request.setPays(pays);
        

        return this.mockMvc.perform(MockMvcRequestBuilders
            .post(API_URL)
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(request))
        );
    }
    
    
    @Test
    void shouldUpdateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(EDITEUR_NAME, EDITEUR_PAYS);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(EDITEUR_NAME, EDITEUR_PAYS);

        // then
        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateStatusOk() throws Exception {
        // given
    	Mockito.when(srv.getById(EDITEUR_ID)).thenReturn(Optional.of(new Editeur()));

        // when
        ResultActions result = this.createAndPut(EDITEUR_NAME, EDITEUR_PAYS);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
    	Editeur e = new Editeur();
        e.setId(EDITEUR_ID);

        Mockito.when(srv.getById(EDITEUR_ID)).thenReturn(Optional.of(e));
        ArgumentCaptor<Editeur> EditeurCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPut(EDITEUR_NAME, EDITEUR_PAYS);

        // then
        Mockito.verify(this.srv).update(EditeurCaptor.capture());

        Editeur Editeur = EditeurCaptor.getValue();

        Assertions.assertEquals(EDITEUR_NAME, Editeur.getNom());
        Assertions.assertEquals(EDITEUR_PAYS, Editeur.getPays());
    }

    @ParameterizedTest
    @CsvSource({
    	"'',pays",
        "'  ', pays",
        ",pays",
        "nom,",
        "nom,''",
        "nom, '     '"
    })
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateStatusBadRequest(String nom, String pays) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(nom, pays);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.any());
    }

    private ResultActions createAndPut(String nom, String pays) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        EditeurRequest request = new EditeurRequest();

        request.setNom(nom);
        request.setPays(pays);

        return this.mockMvc.perform(MockMvcRequestBuilders
            .put(API_URL_BY_ID)
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(request))
        );
    }
    
    @Test
    void shouldDeleteStatusUnauthorized() throws Exception {
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        );

        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldDeleteStatusForbidden() throws Exception {
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        );

        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteStatusOk() throws Exception {

        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUseServiceDeleteById() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        );

        Mockito.verify(this.srv).deleteById(EDITEUR_ID);
    }
    

}
