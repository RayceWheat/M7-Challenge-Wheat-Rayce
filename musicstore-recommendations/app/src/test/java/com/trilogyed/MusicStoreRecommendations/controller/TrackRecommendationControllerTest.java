package com.trilogyed.MusicStoreRecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.MusicStoreRecommendations.model.TrackRecommendation;
import com.trilogyed.MusicStoreRecommendations.repository.TrackRecommendationRepository;
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
@WebMvcTest(TrackRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackRecommendationRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewTrackRecommendationOnPostRequest() throws Exception{

        //Arrange
        TrackRecommendation inTrackRecommendation = new TrackRecommendation();
        inTrackRecommendation.setTrackId(1);
        inTrackRecommendation.setId(10);
        inTrackRecommendation.setUserId(100);
        inTrackRecommendation.setLiked(false);
        //
        TrackRecommendation outTrackRecommendation = new TrackRecommendation();
        outTrackRecommendation.setTrackId(1);
        outTrackRecommendation.setId(10);
        outTrackRecommendation.setUserId(100);
        outTrackRecommendation.setLiked(false);

        String inputJson = mapper.writeValueAsString(inTrackRecommendation);
        String outputJson = mapper.writeValueAsString(outTrackRecommendation);
        //act
        when(repo.save(inTrackRecommendation)).thenReturn(outTrackRecommendation);

        mockMvc.perform(post("/trackRec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldReturnTrackRecommendationById() throws Exception{
        //Arrange
        //Mock
        TrackRecommendation inTrackRecommendation = new TrackRecommendation();
        inTrackRecommendation.setTrackId(1);
        inTrackRecommendation.setId(10);
        inTrackRecommendation.setUserId(100);
        inTrackRecommendation.setLiked(false);

        //Test controller route
        when(repo.findById(10)).thenReturn(Optional.of(inTrackRecommendation));

        //Act and assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trackRec/{id}", 10)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(10));
    }

    @Test
    public void shouldFailGetTrackRecommendationBadIdReturns404() throws Exception{
        String outputJson = null;

        TrackRecommendation album = new TrackRecommendation();
        outputJson = mapper.writeValueAsString(album);
        when(repo.findById(100)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trackRec/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateTrackRecommendation() throws Exception{

        String inputJson = null;

        //Arrange
        TrackRecommendation inTrackRecommendation = new TrackRecommendation();
        inTrackRecommendation.setTrackId(1);
        inTrackRecommendation.setId(10);
        inTrackRecommendation.setUserId(100);
        inTrackRecommendation.setLiked(false);

        inputJson = mapper.writeValueAsString(inTrackRecommendation);

        mockMvc.perform(put("/trackRec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteTrackRecommendation() throws Exception{

        String inputJson = null;

        //Arrange
        TrackRecommendation inTrackRecommendation = new TrackRecommendation();
        inTrackRecommendation.setTrackId(1);
        inTrackRecommendation.setId(1);
        inTrackRecommendation.setUserId(100);
        inTrackRecommendation.setLiked(false);

        inputJson = mapper.writeValueAsString(inTrackRecommendation);

        doNothing().when(repo).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/trackRec/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllTrackRecommendations() throws Exception{

        String outputJson = null;

        //Arrange
        TrackRecommendation savedTrackRecommendation1 = new TrackRecommendation();
        savedTrackRecommendation1.setTrackId(4);
        savedTrackRecommendation1.setUserId(20);
        savedTrackRecommendation1.setLiked(true);
        savedTrackRecommendation1.setId(10);

        TrackRecommendation savedTrackRecommendation2 = new TrackRecommendation();
        savedTrackRecommendation2.setTrackId(80);
        savedTrackRecommendation2.setUserId(40);
        savedTrackRecommendation2.setLiked(false);
        savedTrackRecommendation2.setId(12);

        List<TrackRecommendation> foundList = new ArrayList();
        foundList.add(savedTrackRecommendation1);
        foundList.add(savedTrackRecommendation2);

        outputJson = mapper.writeValueAsString(foundList);

        when(repo.findAll()).thenReturn(foundList);

        mockMvc.perform(MockMvcRequestBuilders.get("/trackRec"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldFailCreateTrackRecommendationWithInvalidData() throws Exception{

        TrackRecommendation savedTrackRecommendation2 = new TrackRecommendation();
        savedTrackRecommendation2.setTrackId(10);
        savedTrackRecommendation2.setUserId(9);


        String inputJson = mapper.writeValueAsString(savedTrackRecommendation2);

        when(repo.save(savedTrackRecommendation2)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/trackRec")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }


}