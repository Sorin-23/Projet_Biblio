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
import biblio.dto.request.CollectionRequest;
import biblio.model.Collection;
import biblio.service.CollectionService;

@WebMvcTest(controllers = CollectionRestController.class, excludeFilters = {
	    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtHeaderFilter.class)})
@EnableMethodSecurity(prePostEnabled = true)
public class CollectionRestControllerTest {
	
	private static final int COLLECTION_ID = 1;
    private static final String COLLECTION_NAME = "Folio Classique";
    private static final String API_URL = "/api/collection";
    private static final String API_URL_BY_ID = API_URL + "/" + COLLECTION_ID;
    
    @MockitoBean
    private CollectionService srv;
    
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
        Collection c1 = new Collection();

        c1.setId(COLLECTION_ID);
        c1.setNom(COLLECTION_NAME);
        

        Mockito.when(this.srv.getAll()).thenReturn(List.of(c1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].nom").exists());
     
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
        Mockito.when(this.srv.getById(COLLECTION_ID)).thenReturn(Optional.of( new Collection()));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(COLLECTION_ID)).thenReturn(Optional.of(new Collection()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(COLLECTION_ID);
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
        Collection c1 = new Collection();

        c1.setId(COLLECTION_ID);
        c1.setNom(COLLECTION_NAME);
     
        Mockito.when(this.srv.getById(COLLECTION_ID)).thenReturn(Optional.of(c1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nom").exists());
    }
    
    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(COLLECTION_NAME);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(COLLECTION_NAME);

        // then
        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(COLLECTION_NAME);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<Collection> collectionCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPost(COLLECTION_NAME);

        // then
        Mockito.verify(this.srv).create(collectionCaptor.capture());

        Collection collection = collectionCaptor.getValue();

        Assertions.assertEquals(COLLECTION_NAME, collection.getNom());
        
    }

    
    @ParameterizedTest
    @CsvSource({
        "''",
        "'   '"
    })
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldCreateStatusBadRequest(String nom) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(nom);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String nom) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CollectionRequest request = new CollectionRequest();

        request.setNom(nom);

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
        ResultActions result = this.createAndPut(COLLECTION_NAME);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(COLLECTION_NAME);

        // then
        result.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateStatusOk() throws Exception {
        // given
    	Mockito.when(srv.getById(COLLECTION_ID)).thenReturn(Optional.of(new Collection()));

        // when
        ResultActions result = this.createAndPut(COLLECTION_NAME);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
    	Collection c = new Collection();
        c.setId(COLLECTION_ID);

        Mockito.when(srv.getById(COLLECTION_ID)).thenReturn(Optional.of(c));
        ArgumentCaptor<Collection> collectionCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPut(COLLECTION_NAME);

        // then
        Mockito.verify(this.srv).update(collectionCaptor.capture());

        Collection collection = collectionCaptor.getValue();

        Assertions.assertEquals(COLLECTION_NAME, collection.getNom());
    }

    @ParameterizedTest
    @CsvSource({
        "''",
        "'   '"
    })
    @WithMockUser(roles = {"ADMIN", "EDITEUR"})
    void shouldUpdateStatusBadRequest(String nom) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPut(nom);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.any());
    }
    

    private ResultActions createAndPut(String nom) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CollectionRequest request = new CollectionRequest();

        request.setNom(nom);

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

        Mockito.verify(this.srv).deleteById(COLLECTION_ID);
    }
    
    
}
