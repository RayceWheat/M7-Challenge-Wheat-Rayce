package com.trilogyed.MusicStoreRecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.MusicStoreRecommendations.model.AlbumRecommendation;
import com.trilogyed.MusicStoreRecommendations.repository.AlbumRecommendationRepository;
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
@WebMvcTest(AlbumRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRecommendationRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewAlbumRecommendationOnPostRequest() throws Exception{

        //Arrange
        AlbumRecommendation inAlbumRecommendation = new AlbumRecommendation();
        inAlbumRecommendation.setAlbumId(10);
        inAlbumRecommendation.setLiked(true);
        inAlbumRecommendation.setUserId(36);
        inAlbumRecommendation.setId(12);


        //
        AlbumRecommendation outAlbumRecommendation = new AlbumRecommendation();
        outAlbumRecommendation.setAlbumId(10);
        outAlbumRecommendation.setLiked(true);
        outAlbumRecommendation.setUserId(36);
        outAlbumRecommendation.setId(12);

        String inputJson = mapper.writeValueAsString(inAlbumRecommendation);
        String outputJson = mapper.writeValueAsString(outAlbumRecommendation);
        //act
        when(repo.save(inAlbumRecommendation)).thenReturn(outAlbumRecommendation);

        mockMvc.perform(post("/albumRec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldReturnAlbumRecommendationById() throws Exception{
        //Arrange
        //Mock
        AlbumRecommendation inAlbumRecommendation = new AlbumRecommendation();
        inAlbumRecommendation.setAlbumId(100);
        inAlbumRecommendation.setLiked(false);
        inAlbumRecommendation.setUserId(362);
        inAlbumRecommendation.setId(1);

        //Test controller route
        when(repo.findById(1)).thenReturn(Optional.of(inAlbumRecommendation));

        //Act and assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/albumRec/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldFailGetAlbumRecommendationBadIdReturns404() throws Exception{
        String outputJson = null;

        AlbumRecommendation album = new AlbumRecommendation();
        outputJson = mapper.writeValueAsString(album);
        when(repo.findById(100)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/albumRec/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() throws Exception{

        String inputJson = null;

        //Arrange
        AlbumRecommendation inAlbumRecommendation = new AlbumRecommendation();
        inAlbumRecommendation.setAlbumId(7);
        inAlbumRecommendation.setLiked(true);
        inAlbumRecommendation.setUserId(46);
        inAlbumRecommendation.setId(4);

        inputJson = mapper.writeValueAsString(inAlbumRecommendation);

        mockMvc.perform(put("/albumRec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteAlbumRecommendation() throws Exception{

        String inputJson = null;

        //Arrange
        AlbumRecommendation inAlbumRecommendation = new AlbumRecommendation();
        inAlbumRecommendation.setAlbumId(7);
        inAlbumRecommendation.setLiked(true);
        inAlbumRecommendation.setUserId(46);
        inAlbumRecommendation.setId(1);

        inputJson = mapper.writeValueAsString(inAlbumRecommendation);

        doNothing().when(repo).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/albumRec/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllAlbumRecommendations() throws Exception{

        String outputJson = null;

        //Arrange
        AlbumRecommendation savedAlbumRecommendation1 = new AlbumRecommendation();
        savedAlbumRecommendation1.setAlbumId(7);
        savedAlbumRecommendation1.setLiked(true);
        savedAlbumRecommendation1.setUserId(46);
        savedAlbumRecommendation1.setId(10);

        AlbumRecommendation savedAlbumRecommendation2 = new AlbumRecommendation();
        savedAlbumRecommendation2.setAlbumId(100);
        savedAlbumRecommendation2.setLiked(false);
        savedAlbumRecommendation2.setUserId(3);
        savedAlbumRecommendation2.setId(12);

        List<AlbumRecommendation> foundList = new ArrayList();
        foundList.add(savedAlbumRecommendation1);
        foundList.add(savedAlbumRecommendation2);

        outputJson = mapper.writeValueAsString(foundList);

        when(repo.findAll()).thenReturn(foundList);

        mockMvc.perform(MockMvcRequestBuilders.get("/albumRec"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldFailCreateAlbumRecommendationWithInvalidData() throws Exception{

        AlbumRecommendation savedAlbumRecommendation2 = new AlbumRecommendation();
        savedAlbumRecommendation2.setAlbumId(100);
        savedAlbumRecommendation2.setUserId(3);
        savedAlbumRecommendation2.setId(12);

        String inputJson = mapper.writeValueAsString(savedAlbumRecommendation2);

        when(repo.save(savedAlbumRecommendation2)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/albumRec")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }


}