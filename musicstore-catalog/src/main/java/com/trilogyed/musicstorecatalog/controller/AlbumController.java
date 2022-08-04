package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/album")
public class AlbumController {
    
    @Autowired
    AlbumRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbums(){
        List<Album> allAlbums = repo.findAll();
        if (allAlbums == null || allAlbums.isEmpty()){
            throw new IllegalArgumentException("No albums were found");
        } else {
            return allAlbums;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody @Valid Album album){
        repo.save(album);
        return album;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@RequestBody @Valid Album album){
        if (album == null || album.getId() < 1){
            throw new IllegalArgumentException("Album does not exists with this Id");
        } else if (album.getId() > 0){
            repo.save(album);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable("id") long albumId){
        Optional<Album> album = repo.findById(albumId);
        if (album == null){
            throw new IllegalArgumentException("Album not found with this id");
        } else {
            return (album.get());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumById(@PathVariable("id") long albumId){
        repo.deleteById(albumId);
    }
    
}
