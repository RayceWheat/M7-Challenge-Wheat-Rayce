package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewAlbumOnPostRequest() throws Exception{

        //Arrange
        Album inAlbum = new Album();
        inAlbum.setTitle("Ready to Die");
        inAlbum.setArtistId(1);
        inAlbum.setReleaseDate(LocalDate.ofEpochDay(1999-10-13));
        inAlbum.setLabelId(1);
        inAlbum.setId(1);
        inAlbum.setListPrice(new BigDecimal("12.99"));

        //
        Album outAlbum = new Album();
        outAlbum.setTitle("Ready to Die");
        outAlbum.setArtistId(1);
        outAlbum.setReleaseDate(LocalDate.ofEpochDay(1999-10-13));
        outAlbum.setLabelId(1);
        outAlbum.setId(1);
        outAlbum.setListPrice(new BigDecimal("12.99"));

        String inputJson = mapper.writeValueAsString(inAlbum);
        String outputJson = mapper.writeValueAsString(outAlbum);
        //act
        when(repo.save(inAlbum)).thenReturn(outAlbum);

        mockMvc.perform(post("/album")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldReturnAlbumById() throws Exception{
        //Arrange
        //Mock
        Album inAlbum = new Album();
        inAlbum.setTitle("Ready to Die");
        inAlbum.setArtistId(1);
        inAlbum.setReleaseDate(LocalDate.ofEpochDay(1999-10-13));
        inAlbum.setLabelId(1);
        inAlbum.setId(1);

        //Test controller route
        when(repo.findById(1)).thenReturn(Optional.of(inAlbum));

        //Act and assert
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/album/{id}", 1)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldFailGetAlbumBadIdReturns404() throws Exception{
        String outputJson = null;

        Album album = new Album();
        outputJson = mapper.writeValueAsString(album);
        when(repo.findById(100)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/album/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateAlbum() throws Exception{

        String inputJson = null;

        //Arrange
        Album inAlbum = new Album();
        inAlbum.setTitle("Ready to Die");
        inAlbum.setArtistId(1);
        inAlbum.setReleaseDate(LocalDate.ofEpochDay(1999-10-13));
        inAlbum.setLabelId(1);
        inAlbum.setId(1);
        inAlbum.setListPrice(new BigDecimal("12.99"));

        inputJson = mapper.writeValueAsString(inAlbum);

        mockMvc.perform(put("/album")
                    .content(inputJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteAlbum() throws Exception{

        String inputJson = null;

        //Arrange
        Album inAlbum = new Album();
        inAlbum.setTitle("Ready to Die");
        inAlbum.setArtistId(1);
        inAlbum.setReleaseDate(LocalDate.ofEpochDay(1999-10-13));
        inAlbum.setLabelId(1);
        inAlbum.setId(1);
        inAlbum.setListPrice(new BigDecimal("12.99"));

        inputJson = mapper.writeValueAsString(inAlbum);

        doNothing().when(repo).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/album/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllAlbums() throws Exception{

        String outputJson = null;

        //Arrange
        Album savedAlbum1 = new Album();
        savedAlbum1.setTitle("Mister Bob Dobalina");
        savedAlbum1.setArtistId(1);
        savedAlbum1.setReleaseDate(LocalDate.ofEpochDay(1999-10-10));
        savedAlbum1.setListPrice(new BigDecimal("32.99"));
        savedAlbum1.setLabelId(2);
        savedAlbum1.setId(10);

        Album savedAlbum2 = new Album();
        savedAlbum2.setTitle("Big Super Album");
        savedAlbum2.setArtistId(2);
        savedAlbum2.setReleaseDate(LocalDate.ofEpochDay(2000-11-10));
        savedAlbum2.setListPrice(new BigDecimal("32.99"));
        savedAlbum2.setLabelId(3);
        savedAlbum2.setId(12);

        List<Album> foundList = new ArrayList();
        foundList.add(savedAlbum1);
        foundList.add(savedAlbum2);

        outputJson = mapper.writeValueAsString(foundList);

        when(repo.findAll()).thenReturn(foundList);

        mockMvc.perform(MockMvcRequestBuilders.get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldFailCreateAlbumWithInvalidData() throws Exception{

        Album savedAlbum2 = new Album();
        savedAlbum2.setArtistId(2);
        savedAlbum2.setReleaseDate(LocalDate.ofEpochDay(2000-11-10));
        savedAlbum2.setListPrice(new BigDecimal("32.99"));
        savedAlbum2.setLabelId(3);
        savedAlbum2.setId(12);

        String inputJson = mapper.writeValueAsString(savedAlbum2);

        when(repo.save(savedAlbum2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/album")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

}