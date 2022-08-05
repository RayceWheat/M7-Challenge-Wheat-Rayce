package com.trilogyed.MusicStoreRecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.MusicStoreRecommendations.model.LabelRecommendation;
import com.trilogyed.MusicStoreRecommendations.repository.LabelRecommendationRepository;
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
@WebMvcTest(LabelRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LabelRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelRecommendationRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewLabelRecommendationOnPostRequest() throws Exception{

        //Arrange
        LabelRecommendation inLabelRecommendation = new LabelRecommendation();
        inLabelRecommendation.setId(1);
        inLabelRecommendation.setLabelId(10);
        inLabelRecommendation.setUserId(132);
        inLabelRecommendation.setLiked(true);

        //
        LabelRecommendation outLabelRecommendation = new LabelRecommendation();
        outLabelRecommendation.setId(1);
        outLabelRecommendation.setLabelId(10);
        outLabelRecommendation.setUserId(132);
        outLabelRecommendation.setLiked(true);

        String inputJson = mapper.writeValueAsString(inLabelRecommendation);
        String outputJson = mapper.writeValueAsString(outLabelRecommendation);
        //act
        when(repo.save(inLabelRecommendation)).thenReturn(outLabelRecommendation);

        mockMvc.perform(post("/labelRec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldReturnLabelRecommendationById() throws Exception{
        //Arrange
        //Mock
        LabelRecommendation inLabelRecommendation = new LabelRecommendation();
        inLabelRecommendation.setId(12);
        inLabelRecommendation.setLabelId(30);
        inLabelRecommendation.setUserId(400);
        inLabelRecommendation.setLiked(true);


        //Test controller route
        when(repo.findById(12)).thenReturn(Optional.of(inLabelRecommendation));

        //Act and assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/labelRec/{id}", 12)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(12));
    }

    @Test
    public void shouldFailGetLabelRecommendationBadIdReturns404() throws Exception{
        String outputJson = null;

        LabelRecommendation album = new LabelRecommendation();
        outputJson = mapper.writeValueAsString(album);
        when(repo.findById(100)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/labelRec/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateLabelRecommendation() throws Exception{

        String inputJson = null;

        //Arrange
        LabelRecommendation inLabelRecommendation = new LabelRecommendation();
        inLabelRecommendation.setId(1);
        inLabelRecommendation.setLabelId(10);
        inLabelRecommendation.setUserId(132);
        inLabelRecommendation.setLiked(true);

        inputJson = mapper.writeValueAsString(inLabelRecommendation);

        mockMvc.perform(put("/labelRec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteLabelRecommendation() throws Exception{

        String inputJson = null;

        //Arrange
        LabelRecommendation inLabelRecommendation = new LabelRecommendation();
        inLabelRecommendation.setId(1);
        inLabelRecommendation.setLabelId(10);
        inLabelRecommendation.setUserId(132);
        inLabelRecommendation.setLiked(true);

        inputJson = mapper.writeValueAsString(inLabelRecommendation);

        doNothing().when(repo).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/labelRec/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllLabelRecommendations() throws Exception{

        String outputJson = null;

        //Arrange
        LabelRecommendation savedLabelRecommendation1 = new LabelRecommendation();
        savedLabelRecommendation1.setId(12);
        savedLabelRecommendation1.setLabelId(1);
        savedLabelRecommendation1.setUserId(15);
        savedLabelRecommendation1.setLiked(false);


        LabelRecommendation savedLabelRecommendation2 = new LabelRecommendation();
        savedLabelRecommendation2.setId(1020);
        savedLabelRecommendation2.setLabelId(10);
        savedLabelRecommendation2.setUserId(500);
        savedLabelRecommendation2.setLiked(true);

        List<LabelRecommendation> foundList = new ArrayList();
        foundList.add(savedLabelRecommendation1);
        foundList.add(savedLabelRecommendation2);

        outputJson = mapper.writeValueAsString(foundList);

        when(repo.findAll()).thenReturn(foundList);

        mockMvc.perform(MockMvcRequestBuilders.get("/labelRec"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldFailCreateLabelRecommendationWithInvalidData() throws Exception{

        LabelRecommendation savedLabelRecommendation2 = new LabelRecommendation();
        savedLabelRecommendation2.setId(1020);
        savedLabelRecommendation2.setLabelId(10);
        savedLabelRecommendation2.setUserId(500);

        String inputJson = mapper.writeValueAsString(savedLabelRecommendation2);

        when(repo.save(savedLabelRecommendation2)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/labelRec")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }


}