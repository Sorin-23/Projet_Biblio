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
import biblio.dto.request.AuteurRequest;
import biblio.model.Auteur;
import biblio.service.AuteurService;

@WebMvcTest(controllers = AuteurRestController.class, excludeFilters = {
	    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtHeaderFilter.class)})
@EnableMethodSecurity(prePostEnabled = true)
public class AuteurRestControllerTest {
	private static final int AUTEUR_ID = 1;
    private static final String AUTEUR_NAME = "Dumas";
    private static final String AUTEUR_PRENOM = "Alexandre";
    private static final String AUTEUR_NATIONALITE = "Française";
    private static final String API_URL = "/api/auteur";
    private static final String API_URL_BY_ID = API_URL + "/" + AUTEUR_ID;
    
    @MockitoBean
    private AuteurService srv;
    
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
    void shouldGetAllReturnAttributes() throws Exception {

        // --- GIVEN ---
        Auteur a1 = new Auteur();
        a1.setId(1);
        a1.setNom("Dumas");
        a1.setPrenom("Alexandre");
        a1.setNationalite("Française");

        // On retourne la liste de Auteur
        Mockito.when(srv.getAll()).thenReturn(List.of(a1));

        // --- WHEN ---
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/auteur")
        );

        // --- THEN ---
        
        result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Dumas"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].prenom").value("Alexandre"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].nationalite").value("Française"));
    }
    
    @Test
    void shouldGetByIdStatusUnauthorized() throws Exception {
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
    	Mockito.when(this.srv.getById(AUTEUR_ID)).thenReturn(Optional.of(new Auteur()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(AUTEUR_ID)).thenReturn(Optional.of(new Auteur()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(AUTEUR_ID);
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
        Auteur a1 = new Auteur();

        a1.setId(AUTEUR_ID);
        a1.setNom(AUTEUR_NAME);
        a1.setPrenom(AUTEUR_PRENOM);
        a1.setNationalite(AUTEUR_NATIONALITE);
        Mockito.when(this.srv.getById(AUTEUR_ID)).thenReturn(Optional.of(a1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.prenom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nationalite").exists());
    }
    
    
    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(AUTEUR_NAME, AUTEUR_PRENOM, AUTEUR_NATIONALITE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(AUTEUR_NAME, AUTEUR_PRENOM, AUTEUR_NATIONALITE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(AUTEUR_NAME, AUTEUR_PRENOM, AUTEUR_NATIONALITE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<Auteur> auteurCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPost(AUTEUR_NAME, AUTEUR_PRENOM, AUTEUR_NATIONALITE);

        // then
        Mockito.verify(this.srv).create(auteurCaptor.capture());

        Auteur auteur = auteurCaptor.getValue();

        Assertions.assertEquals(AUTEUR_NAME, auteur.getNom());
        Assertions.assertEquals(AUTEUR_PRENOM, auteur.getPrenom());
        Assertions.assertEquals(AUTEUR_NATIONALITE, auteur.getNationalite());
    }

    @ParameterizedTest
    @CsvSource({
        "'','',nationalite",
        "'  ','   ', nationalite",
        ",,nationalitee",
        "nom,,",
        "nom,'',''",
        "nom,'     ', '     '",
        "'',prenom,''",
        "'    ',prenom,'   '",
        ",prenom,"
    })
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateStatusBadRequest(String nom, String prenom, String nationalite) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(nom, prenom, nationalite);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String nom, String prenom, String nationalite) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuteurRequest request = new AuteurRequest();

        request.setNom(nom);
        request.setPrenom(prenom);
        request.setNationalite(nationalite);

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
        ResultActions result = this.createAndPut(AUTEUR_NAME, AUTEUR_PRENOM, AUTEUR_NATIONALITE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(AUTEUR_NAME, AUTEUR_PRENOM, AUTEUR_NATIONALITE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateStatusOk() throws Exception {
        // given
    	Mockito.when(srv.getById(AUTEUR_ID)).thenReturn(Optional.of(new Auteur()));

        // when
        ResultActions result = this.createAndPut(AUTEUR_NAME, AUTEUR_PRENOM, AUTEUR_NATIONALITE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
    	Auteur a = new Auteur();
        a.setId(AUTEUR_ID);

        Mockito.when(srv.getById(AUTEUR_ID)).thenReturn(Optional.of(a));
        ArgumentCaptor<Auteur> auteurCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPut(AUTEUR_NAME, AUTEUR_PRENOM, AUTEUR_NATIONALITE);

        // then
        Mockito.verify(this.srv).update(auteurCaptor.capture());

        Auteur auteur = auteurCaptor.getValue();

        Assertions.assertEquals(AUTEUR_NAME, auteur.getNom());
        Assertions.assertEquals(AUTEUR_PRENOM, auteur.getPrenom());
        Assertions.assertEquals(AUTEUR_NATIONALITE, auteur.getNationalite());
    }

    @ParameterizedTest
    @CsvSource({
        "'','',nationalite",
        "'  ','   ', nationalite",
        ",,nationalitee",
        "nom,,",
        "nom,'',''",
        "nom,'     ', '     '",
        "'',prenom,''",
        "'    ',prenom,'   '",
        ",prenom,"
    })
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateStatusBadRequest(String nom, String prenom, String nationalite) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(nom, prenom, nationalite);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.any());
    }

    private ResultActions createAndPut(String nom, String prenom, String nationalite) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuteurRequest request = new AuteurRequest();

        request.setNom(nom);
        request.setPrenom(prenom);
        request.setNationalite(nationalite);

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

        Mockito.verify(this.srv).deleteById(AUTEUR_ID);
    }
    
}
