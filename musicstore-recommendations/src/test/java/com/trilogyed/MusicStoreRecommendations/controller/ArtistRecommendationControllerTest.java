package com.trilogyed.MusicStoreRecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.MusicStoreRecommendations.model.ArtistRecommendation;
import com.trilogyed.MusicStoreRecommendations.repository.ArtistRecommendationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@WebMvcTest(ArtistRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRecommendationRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewArtistRecommendationOnPostRequest() throws Exception{

        //Arrange
        ArtistRecommendation inArtistRecommendation = new ArtistRecommendation();
        inArtistRecommendation.setUserId(1337);
        inArtistRecommendation.setArtistId(1);
        inArtistRecommendation.setId(1);
        inArtistRecommendation.setLiked(true);

        //
        ArtistRecommendation outArtistRecommendation = new ArtistRecommendation();
        outArtistRecommendation.setUserId(1337);
        outArtistRecommendation.setArtistId(1);
        outArtistRecommendation.setId(1);
        outArtistRecommendation.setLiked(true);

        String inputJson = mapper.writeValueAsString(inArtistRecommendation);
        String outputJson = mapper.writeValueAsString(outArtistRecommendation);
        //act
        when(repo.save(inArtistRecommendation)).thenReturn(outArtistRecommendation);

        mockMvc.perform(post("/artistRec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldReturnArtistRecommendationById() throws Exception{
        //Arrange
        //Mock
        ArtistRecommendation inArtistRecommendation = new ArtistRecommendation();
        inArtistRecommendation.setUserId(1337);
        inArtistRecommendation.setArtistId(12);
        inArtistRecommendation.setId(1);
        inArtistRecommendation.setLiked(true);

        //Test controller route
        when(repo.findById(1)).thenReturn(Optional.of(inArtistRecommendation));

        //Act and assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/artistRec/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldFailGetArtistRecommendationBadIdReturns404() throws Exception{
        String outputJson = null;

        ArtistRecommendation album = new ArtistRecommendation();
        outputJson = mapper.writeValueAsString(album);
        when(repo.findById(100)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/artistRec/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateArtistRecommendation() throws Exception{

        String inputJson = null;

        //Arrange
        ArtistRecommendation inArtistRecommendation = new ArtistRecommendation();
        inArtistRecommendation.setUserId(1337);
        inArtistRecommendation.setArtistId(1);
        inArtistRecommendation.setId(1);
        inArtistRecommendation.setLiked(true);

        inputJson = mapper.writeValueAsString(inArtistRecommendation);

        mockMvc.perform(put("/artistRec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteArtistRecommendation() throws Exception{

        String inputJson = null;

        //Arrange
        ArtistRecommendation inArtistRecommendation = new ArtistRecommendation();
        inArtistRecommendation.setUserId(337);
        inArtistRecommendation.setArtistId(12);
        inArtistRecommendation.setId(32);
        inArtistRecommendation.setLiked(false);

        inputJson = mapper.writeValueAsString(inArtistRecommendation);

        doNothing().when(repo).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/artistRec/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllArtistRecommendations() throws Exception{

        String outputJson = null;

        //Arrange
        ArtistRecommendation savedArtistRecommendation1 = new ArtistRecommendation();
        savedArtistRecommendation1.setUserId(1337);
        savedArtistRecommendation1.setArtistId(1);
        savedArtistRecommendation1.setId(1);
        savedArtistRecommendation1.setLiked(true);

        ArtistRecommendation savedArtistRecommendation2 = new ArtistRecommendation();
        savedArtistRecommendation2.setUserId(2);
        savedArtistRecommendation2.setArtistId(14);
        savedArtistRecommendation2.setId(63);
        savedArtistRecommendation2.setLiked(true);


        List<ArtistRecommendation> foundList = new ArrayList();
        foundList.add(savedArtistRecommendation1);
        foundList.add(savedArtistRecommendation2);

        outputJson = mapper.writeValueAsString(foundList);

        when(repo.findAll()).thenReturn(foundList);

        mockMvc.perform(MockMvcRequestBuilders.get("/artistRec"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldFailCreateArtistRecommendationWithInvalidData() throws Exception{

        ArtistRecommendation savedArtistRecommendation2 = new ArtistRecommendation();
        savedArtistRecommendation2.setUserId(2);
        savedArtistRecommendation2.setArtistId(14);
        savedArtistRecommendation2.setId(63);

        String inputJson = mapper.writeValueAsString(savedArtistRecommendation2);

        when(repo.save(savedArtistRecommendation2)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/artistRec")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }


}