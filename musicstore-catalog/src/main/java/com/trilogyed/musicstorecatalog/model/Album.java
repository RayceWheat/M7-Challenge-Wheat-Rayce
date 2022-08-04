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
    private long id;

    @NotEmpty(message = "Title must not be empty")
    private String title;

   // @NotNull(message = "Artist id must not be null")
    @Column(name = "artist_id")
    private long artistId;

   // @NotEmpty(message = "Release date must not be null")
    @Column(name = "release_date")
    private LocalDate releaseDate;

  // @NotNull(message = "Label id must not be empty")
    @Column(name = "label_id")
    private long labelId;

    @NotNull(message = "Price must be included ")
    @DecimalMin(value = "0.0", inclusive = true, message = "Min price is $0.0")
    @DecimalMax(value = "999.99", inclusive = true, message = "Max price is $999.99")
    @Column(name = "list_price")
    private BigDecimal listPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getLabelId() {
        return labelId;
    }

    public void setLabelId(long labelId) {
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
        return id == album.id && artistId == album.artistId && labelId == album.labelId && Objects.equals(title, album.title) && Objects.equals(releaseDate, album.releaseDate) && Objects.equals(listPrice, album.listPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artistId, releaseDate, labelId, listPrice);
    }
}
