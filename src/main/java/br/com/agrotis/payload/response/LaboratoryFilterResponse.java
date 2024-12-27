package br.com.agrotis.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class LaboratoryFilterResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("quantidade_cadastrados")
    private Integer qtdPeople;

    public LaboratoryFilterResponse() {
    }

    public LaboratoryFilterResponse(Long id, String name, Integer qtdPeople) {
        this.id = Objects.nonNull(id) ? id : null;
        this.name = Objects.nonNull(name) ? name.toUpperCase() : null;
        this.qtdPeople = Objects.nonNull(qtdPeople) ? qtdPeople : null;
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

    public Integer getQtdPeople() {
        return qtdPeople;
    }

    public void setQtdPeople(Integer qtdPeople) {
        this.qtdPeople = qtdPeople;
    }

}
