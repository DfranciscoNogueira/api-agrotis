package br.com.agrotis.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PersonRequest {

    @NotNull
    @NotBlank
    @Size(max = 50)
    @JsonProperty("nome")
    private String name;

    @JsonProperty("dataInicial")
    private LocalDateTime startDate;

    @JsonProperty("dataFinal")
    private LocalDateTime endDate;

    @JsonProperty("observacoes")
    private String observations;

    @JsonProperty("idPropriedade")
    private Long propertyId;

    @JsonProperty("idLaboratorio")
    private Long laboratoryId;

    public PersonRequest() {
    }

    public PersonRequest(String name, LocalDateTime startDate, LocalDateTime endDate, String observations, Long propertyId, Long laboratoryId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.observations = observations;
        this.propertyId = propertyId;
        this.laboratoryId = laboratoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(Long laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

}
