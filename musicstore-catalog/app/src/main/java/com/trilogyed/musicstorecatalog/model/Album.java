package com.trilogyed.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Integer id;

    @NotEmpty(message = "Title must not be empty")
    private String title;

   // @NotNull(message = "Artist id must not be null")
    @Column(name = "artist_id")
    private Integer artistId;

   // @NotEmpty(message = "Release date must not be null")
    @Column(name = "release_date")
    private LocalDate releaseDate;

  // @NotNull(message = "Label id must not be empty")
    @Column(name = "label_id")
    private Integer labelId;

    @NotNull(message = "Price must be included ")
    @DecimalMin(value = "0.0", inclusive = true, message = "Min price is $0.0")
    @DecimalMax(value = "999.99", inclusive = true, message = "Max price is $999.99")
    @Column(name = "list_price")
    private BigDecimal listPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id) && Objects.equals(title, album.title) && Objects.equals(artistId, album.artistId) && Objects.equals(releaseDate, album.releaseDate) && Objects.equals(labelId, album.labelId) && Objects.equals(listPrice, album.listPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artistId, releaseDate, labelId, listPrice);
    }
}
