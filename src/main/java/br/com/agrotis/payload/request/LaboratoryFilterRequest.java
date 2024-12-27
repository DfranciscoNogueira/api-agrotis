package br.com.agrotis.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class LaboratoryFilterRequest {

    @JsonProperty("observacao")
    private String observation;

    @JsonProperty("dataInicial")
    private LocalDateTime startDate;

    @JsonProperty("dataFinal")
    private LocalDateTime endDate;

    @NotNull
    @JsonProperty("quantidade_minima")
    private Integer qtdMinPeople;

    public LaboratoryFilterRequest() {
    }

    public LaboratoryFilterRequest(String observation, LocalDateTime startDate, LocalDateTime endDate, Integer qtdMinPeople) {
        this.observation = observation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.qtdMinPeople = qtdMinPeople;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

    public Integer getQtdMinPeople() {
        return qtdMinPeople;
    }

    public void setQtdMinPeople(Integer qtdMinPeople) {
        this.qtdMinPeople = qtdMinPeople;
    }

}
