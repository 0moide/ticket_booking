package com.example.mywebsite.dto;

public class FilmDto {
    private Long id;
    private String title;
    private String genre;
    private int duration;
    private int minAge;
    private String description;
    private String posterUrl;

    public FilmDto() {}

    public FilmDto(Long id, String title, String genre, int duration, int minAge, String description, String posterUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.minAge = minAge;
        this.description = description;
        this.posterUrl = posterUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getMinAge() { return minAge; }
    public void setMinAge(int minAge) { this.minAge = minAge; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
}