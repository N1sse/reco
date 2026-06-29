package com.ucr.reco.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class SpaceDTO {

    @NotBlank(message = "Por favor, agregue el nombre del espacio")
    @Size(min = 5, max = 100, message = "El nombre debe tener entre un minimo de 6 caracteres y un máximo de 100 caracteres")
   private String name;

    @NotBlank(message = "Por favor, agregue la ubicación del espacio")
    private String location;

    @NotBlank(message = "Por favor, agregue el tipo de espacio")
    private String type;

    @NotNull(message = "Por favor, agregue el precio del espacio")
    @Positive(message = "El precio debe ser mayor que cero")
    private Double price;

    public SpaceDTO(String name, String location, String type, Double price) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
