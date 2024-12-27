package br.com.agrotis.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PropertyRequest {

    @NotNull
    @NotBlank
    @Size(max = 50)
    @JsonProperty("nome")
    private String name;

    public PropertyRequest() {
    }

    public PropertyRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
