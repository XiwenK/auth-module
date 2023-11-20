package com.manunin.score.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDto {

    @Schema(example = "1", description = "Group id")
    private long id;

    @NotBlank
    @Schema(example = "Analyst", description = "Group name")
    private String name;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @NotNull
    @Schema(example = "12.03.2023 12:40", description = "Group start date")
    private LocalDateTime dateTime;

    @NotNull
    @Schema(example = "20", description = "Group places count")
    private int placesCount;

    @NotNull
    @Schema(example = "10", description = "Group booked")
    private int booked;

    @NotNull
    @Schema(description = "Schedule")
    private ScheduleDto schedule;

    @Schema(description = "Group url")
    private String url;

    @Schema(description = "Group place price")
    private Integer placePrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDto schedule) {
        this.schedule = schedule;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPlacePrice() {
        return placePrice;
    }

    public void setPlacePrice(Integer placePrice) {
        this.placePrice = placePrice;
    }
}
