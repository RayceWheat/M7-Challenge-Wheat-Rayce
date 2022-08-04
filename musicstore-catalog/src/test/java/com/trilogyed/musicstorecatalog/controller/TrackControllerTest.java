package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewTrackOnPostRequest() throws Exception{

        //Arrange
        Track inTrack = new Track();
        inTrack.setTitle("Breakdancin'");
        inTrack.setRunTime(3.12);
        inTrack.setId(1);


        //
        Track outTrack = new Track();
        outTrack.setTitle("Breakdancin'");
        outTrack.setRunTime(3.12);
        outTrack.setId(1);


        String inputJson = mapper.writeValueAsString(inTrack);
        String outputJson = mapper.writeValueAsString(outTrack);
        //act
        when(repo.save(inTrack)).thenReturn(outTrack);

        mockMvc.perform(post("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldReturnTrackById() throws Exception{
        //Arrange
        //Mock
        Track inTrack = new Track();
        inTrack.setTitle("Breakdancin'");
        inTrack.setRunTime(3.12);
        inTrack.setId(1);
        inTrack.setId(1);

        //Test controller route
        when(repo.findById(1)).thenReturn(Optional.of(inTrack));

        //Act and assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/track/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldFailGetTrackBadIdReturns404() throws Exception{
        String outputJson = null;

        Track track = new Track();
      //  outputJson = mapper.writeValueAsString(track);
        when(repo.findById(100)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/track/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateTrack() throws Exception{

        String inputJson = null;

        //Arrange
        Track inTrack = new Track();
        inTrack.setTitle("Breakdancin'");
        inTrack.setRunTime(3.12);
        inTrack.setId(1);

        inputJson = mapper.writeValueAsString(inTrack);

        mockMvc.perform(put("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteTrack() throws Exception{

        String inputJson = null;

        //Arrange
        Track inTrack = new Track();
        inTrack.setTitle("Breakdancin'");
        inTrack.setRunTime(3.12);
        inTrack.setId(1);

        inputJson = mapper.writeValueAsString(inTrack);

        doNothing().when(repo).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/track/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllTracks() throws Exception{

        String outputJson = null;

        //Arrange
        Track savedTrack1 = new Track();
        savedTrack1.setTitle("A Horse with no Name");
        savedTrack1.setRunTime(4.49);
        savedTrack1.setId(10);

        Track savedTrack2 = new Track();
        savedTrack2.setTitle("Cabinet Man");
        savedTrack2.setRunTime(4.5);
        savedTrack2.setId(12);

        List<Track> foundList = new ArrayList();
        foundList.add(savedTrack1);
        foundList.add(savedTrack2);

        outputJson = mapper.writeValueAsString(foundList);

        when(repo.findAll()).thenReturn(foundList);

        mockMvc.perform(MockMvcRequestBuilders.get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldFailCreateTrackWithInvalidData() throws Exception{

        Track savedTrack2 = new Track();
        savedTrack2.setAlbumId(1);
        savedTrack2.setRunTime(12.5);
        savedTrack2.setId(12);

        String inputJson = mapper.writeValueAsString(savedTrack2);

        when(repo.save(savedTrack2)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/track")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

}