package com.trilogyed.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Integer id;

    @NotNull(message = "Album id must not be empty")
    @Column(name = "album_id")
    private Integer albumId;

    @NotEmpty(message = "Title must not be empty")
    private String title;

    @NotNull(message = "Run time must not be empty")
    @Column(name = "run_time")
    private Double runTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRunTime() {
        return runTime;
    }

    public void setRunTime(Double runTime) {
        this.runTime = runTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(id, track.id) && Objects.equals(albumId, track.albumId) && Objects.equals(title, track.title) && Objects.equals(runTime, track.runTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, albumId, title, runTime);
    }
}
