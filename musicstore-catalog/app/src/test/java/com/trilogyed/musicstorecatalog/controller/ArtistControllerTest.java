package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ArtistRepository repo;
    
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewArtistOnPostRequest() throws Exception{

        //Arrange
        Artist inArtist = new Artist();
        inArtist.setName("Biggie Smalls");
        inArtist.setInstagram("@NotoriousBIG");
        inArtist.setTwitter("@BiggieLove");

        //
        Artist outArtist = new Artist();
        outArtist.setName("Biggie Smalls");
        outArtist.setInstagram("@NotoriousBIG");
        outArtist.setTwitter("@BiggieLove");


        String inputJson = mapper.writeValueAsString(inArtist);
        String outputJson = mapper.writeValueAsString(outArtist);
        //act
        when(repo.save(inArtist)).thenReturn(outArtist);

        mockMvc.perform(post("/artist")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldReturnArtistById() throws Exception{
        //Arrange
        //Mock
        Artist inArtist = new Artist();
        inArtist.setName("Biggie Smalls");
        inArtist.setInstagram("@NotoriousBIG");
        inArtist.setTwitter("@BiggieLove");
        inArtist.setId(1);

        //Test controller route
        when(repo.findById(1)).thenReturn(Optional.of(inArtist));

        //Act and assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/artist/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldFailGetArtistBadIdReturns404() throws Exception{
        String outputJson = null;

        Artist artist = new Artist();
        outputJson = mapper.writeValueAsString(artist);
        when(repo.findById(100)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/artist/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateArtist() throws Exception{

        String inputJson = null;

        //Arrange
        Artist inArtist = new Artist();
        inArtist.setName("Biggie Smalls");
        inArtist.setInstagram("@NotoriousBIG");
        inArtist.setTwitter("@BiggieLove");
        inArtist.setId(1);

        inputJson = mapper.writeValueAsString(inArtist);

        mockMvc.perform(put("/artist")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteArtist() throws Exception{

        String inputJson = null;

        //Arrange
        Artist inArtist = new Artist();
        inArtist.setName("Biggie Smalls");
        inArtist.setInstagram("@NotoriousBIG");
        inArtist.setTwitter("@BiggieLove");
        inArtist.setId(1);

        inputJson = mapper.writeValueAsString(inArtist);

        doNothing().when(repo).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/artist/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllArtists() throws Exception{

        String outputJson = null;

        //Arrange
        Artist savedArtist1 = new Artist();
        savedArtist1.setName("Biggie Smalls");
        savedArtist1.setInstagram("@NotoriousBIG");
        savedArtist1.setTwitter("@BiggieLove");
        savedArtist1.setId(10);

        Artist savedArtist2 = new Artist();
        savedArtist2.setName("Britney Spears");
        savedArtist2.setInstagram("BritneyOG");
        savedArtist2.setTwitter("@BadB(tch");
        savedArtist2.setId(12);

        List<Artist> foundList = new ArrayList();
        foundList.add(savedArtist1);
        foundList.add(savedArtist2);

        outputJson = mapper.writeValueAsString(foundList);

        when(repo.findAll()).thenReturn(foundList);

        mockMvc.perform(MockMvcRequestBuilders.get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldFailCreateArtistWithInvalidData() throws Exception{

        Artist savedArtist2 = new Artist();
        savedArtist2.setInstagram("@NotoriousBIG");
        savedArtist2.setTwitter("@BiggieLove");
        savedArtist2.setId(12);

        String inputJson = mapper.writeValueAsString(savedArtist2);

        when(repo.save(savedArtist2)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/artist")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }



}