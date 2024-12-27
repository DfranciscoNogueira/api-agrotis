package br.com.agrotis.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PersonResponse {

    @NotNull
    @JsonProperty("id")
    private Long id;

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

    @JsonProperty("infosPropriedade")
    private PropertyResponse property;

    @JsonProperty("laboratorio")
    private LaboratoryResponse laboratory;

    public PersonResponse() {
    }

    public PersonResponse(Long id, String name, LocalDateTime startDate, LocalDateTime endDate, String observations, PropertyResponse property, LaboratoryResponse laboratory) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.observations = observations;
        this.property = property;
        this.laboratory = laboratory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PropertyResponse getProperty() {
        return property;
    }

    public void setProperty(PropertyResponse property) {
        this.property = property;
    }

    public LaboratoryResponse getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(LaboratoryResponse laboratory) {
        this.laboratory = laboratory;
    }

}
