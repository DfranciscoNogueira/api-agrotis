package br.com.agrotis.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PropertyResponse {

    @NotNull
    @JsonProperty("id")
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @JsonProperty("nome")
    private String name;

    public PropertyResponse() {
    }

    public PropertyResponse(Long id, String name) {
        this.id = id;
        this.name = name;
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

}
