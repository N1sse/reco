package com.ucr.reco.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucr.reco.model.Space;
import com.ucr.reco.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class ReservationDTO {
    private Integer spaceId;

    private String userEmail;
    @JsonFormat(pattern = "dd-MM-yy HH:mm")
     private LocalDateTime startDate;

    @JsonFormat(pattern = "dd-MM-yy HH:mm")
    private LocalDateTime endDate;



    public ReservationDTO() {
    }

    public ReservationDTO(LocalDateTime startDate, LocalDateTime endDate, Integer spaceId, String userEmail) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.spaceId = spaceId;
        this.userEmail = userEmail;
    }

    //set y get
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
