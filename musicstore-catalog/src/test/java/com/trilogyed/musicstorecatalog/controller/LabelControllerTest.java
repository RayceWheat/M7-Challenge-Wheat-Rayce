package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
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
@WebMvcTest(LabelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private LabelRepository repo;
    
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewLabelOnPostRequest() throws Exception{

        //Arrange
        Label inLabel = new Label();
        inLabel.setName("DeathRow Records");
        inLabel.setWebsite("www.DeathRowFakeWebsite");
        inLabel.setId(1L);


        //
        Label outLabel = new Label();
        outLabel.setName("DeathRow Records");
        outLabel.setWebsite("www.DeathRowFakeWebsite");
        outLabel.setId(1L);

        String inputJson = mapper.writeValueAsString(inLabel);
        String outputJson = mapper.writeValueAsString(outLabel);
        //act
        when(repo.save(inLabel)).thenReturn(outLabel);

        mockMvc.perform(post("/label")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldReturnLabelById() throws Exception{
        //Arrange
        //Mock
        Label inLabel = new Label();
        inLabel.setName("DeathRow Records");
        inLabel.setWebsite("www.DeathRowFakeWebsite");
        inLabel.setId(1L);

        //Test controller route
        when(repo.findById(1L)).thenReturn(Optional.of(inLabel));

        //Act and assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/label/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldFailGetLabelBadIdReturns404() throws Exception{
        String outputJson = null;

        Label label = new Label();
        outputJson = mapper.writeValueAsString(label);
        when(repo.findById(100L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/label/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateLabel() throws Exception{

        String inputJson = null;

        //Arrange
        Label inLabel = new Label();
        inLabel.setName("DeathRow Records");
        inLabel.setWebsite("www.DeathRowFakeWebsite");
        inLabel.setId(1L);

        inputJson = mapper.writeValueAsString(inLabel);

        mockMvc.perform(put("/label")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteLabel() throws Exception{

        String inputJson = null;

        //Arrange
        Label inLabel = new Label();
        inLabel.setName("DeathRow Records");
        inLabel.setWebsite("www.DeathRowFakeWebsite");
        inLabel.setId(1L);

        inputJson = mapper.writeValueAsString(inLabel);

        doNothing().when(repo).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/label/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllLabels() throws Exception{

        String outputJson = null;

        //Arrange
        Label savedLabel1 = new Label();
        savedLabel1.setName("Wu-Tang Forever");
        savedLabel1.setWebsite("Wu-Tang Website");
        savedLabel1.setId(10L);

        Label savedLabel2 = new Label();
        savedLabel2.setName("Boogie Down Productions");
        savedLabel2.setWebsite("www.TribeCalledQuest");
        savedLabel2.setId(12L);

        List<Label> foundList = new ArrayList();
        foundList.add(savedLabel1);
        foundList.add(savedLabel2);

        outputJson = mapper.writeValueAsString(foundList);

        when(repo.findAll()).thenReturn(foundList);

        mockMvc.perform(MockMvcRequestBuilders.get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldFailCreateLabelWithInvalidData() throws Exception{

        Label savedLabel2 = new Label();
        savedLabel2.setWebsite("www.TribeCalledQuest");
        savedLabel2.setId(12L);

        String inputJson = mapper.writeValueAsString(savedLabel2);

        when(repo.save(savedLabel2)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/label")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }
    

}