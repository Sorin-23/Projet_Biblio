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
import biblio.dto.request.LivreRequest;
import biblio.model.Livre;
import biblio.service.AuteurService;
import biblio.service.CollectionService;
import biblio.service.EditeurService;
import biblio.service.LivreService;


@WebMvcTest(controllers = LivreRestController.class, excludeFilters = {
	    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtHeaderFilter.class)})
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

@MockitoBean
private AuteurService auteurSrv;

@MockitoBean
private EditeurService editeurSrv;

@MockitoBean
private CollectionService collectionSrv;
    
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

   /* @Test
    @WithMockUser
    void shouldGetByIdStatusOk() throws Exception {
        // given
        Mockito.when(this.srv.getById(LIVRE_ID)).thenReturn(Optional.of(new Livre()));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
*/
    /*@Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(LIVRE_ID)).thenReturn(Optional.of(new Livre()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(LIVRE_ID);
    }
*/
    @Test
    @WithMockUser
    void shouldGetByIdStatusNotFoundWhenIdNotFound() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

   /* @Test
    @WithMockUser
    void shouldGetByIdReturnAttributes() throws Exception {
        // given
        Livre l1 = new Livre();

        l1.setId(LIVRE_ID);
        l1.setTitre(LIVRE_NAME);
        l1.setResume(LIVRE_RESUME);
        l1.setAnnee(LIVRE_ANNEE);
        Mockito.when(this.srv.getById(LIVRE_ID)).thenReturn(Optional.of(l1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.titre").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.resume").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.annee").exists());
    }*/
    
    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(LIVRE_NAME, LIVRE_RESUME, LIVRE_ANNEE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(LIVRE_NAME, LIVRE_RESUME, LIVRE_ANNEE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

   /* @Test
    @WithMockUser(roles = {"ADMIN", "LIVRE"})
    void shouldCreateStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(LIVRE_NAME, LIVRE_RESUME, LIVRE_ANNEE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }*/
    /*
    @Test
    @WithMockUser(roles = {"ADMIN", "LIVRE"})
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<Livre> livreCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPost(LIVRE_NAME, LIVRE_RESUME, LIVRE_ANNEE);

        // then
        Mockito.verify(this.srv).create(livreCaptor.capture());

        Livre livre = livreCaptor.getValue();

        Assertions.assertEquals(LIVRE_NAME, livre.getTitre());
        Assertions.assertEquals(LIVRE_RESUME, livre.getResume());
        Assertions.assertEquals(LIVRE_ANNEE, livre.getAnnee());
       
    }*/

    @ParameterizedTest
    @CsvSource({
    	"'', 'Résumé valide', 2000",
        "'   ', 'Résumé valide', 2000",
        ", 'Résumé valide',0"
        
    })
    @WithMockUser(roles = {"ADMIN", "LIVRE"})
    void shouldCreateStatusBadRequest(String titre, String resume, int annee) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(titre, resume, annee);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String titre, String resume, int annee) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LivreRequest request = new LivreRequest();

        request.setTitre(titre);
        request.setResume(resume);
        request.setAnnee(annee);
        

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
        ResultActions result = this.createAndPut(LIVRE_NAME, LIVRE_RESUME, LIVRE_ANNEE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(LIVRE_NAME, LIVRE_RESUME, LIVRE_ANNEE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    /*@Test
    @WithMockUser(roles = {"ADMIN", "LIVRE"})
    void shouldUpdateStatusOk() throws Exception {
        // given
    	Mockito.when(srv.getById(LIVRE_ID)).thenReturn(Optional.of(new Livre()));

        // when
        ResultActions result = this.createAndPut(LIVRE_NAME, LIVRE_RESUME, LIVRE_ANNEE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }*/

   /* @Test
    @WithMockUser(roles = {"ADMIN", "LIVRE"})
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
    	Livre l = new Livre();
        l.setId(LIVRE_ID);

        Mockito.when(srv.getById(LIVRE_ID)).thenReturn(Optional.of(l));
        ArgumentCaptor<Livre> livreCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPut(LIVRE_NAME, LIVRE_RESUME, LIVRE_ANNEE);

        // then
        Mockito.verify(this.srv).update(livreCaptor.capture());

        Livre livre = livreCaptor.getValue();

        Assertions.assertEquals(LIVRE_NAME, livre.getTitre());
        Assertions.assertEquals(LIVRE_RESUME, livre.getResume());
        Assertions.assertEquals(LIVRE_ANNEE, livre.getAnnee());
    }*/

    @ParameterizedTest
    @CsvSource({
    	"'','',0",
        "'  ','   ', 0",
        ",,0",
        "'',resume,112",
        ",resume,0",
        "'    ',resume,2132"
    })
    @WithMockUser(roles = {"ADMIN", "LIVRE"})
    void shouldUpdateStatusBadRequest(String titre, String resume, int annee) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(titre,resume, annee);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.any());
    }

    private ResultActions createAndPut(String titre, String resume, int annee) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LivreRequest request = new LivreRequest();

        request.setTitre(titre);
        request.setResume(resume);
        request.setAnnee(annee);

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
    	Mockito.doNothing().when(srv).deleteById(LIVRE_ID);

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.srv).deleteById(LIVRE_ID);
    }
    
    
}

