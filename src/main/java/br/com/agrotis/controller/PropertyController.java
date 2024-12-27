package br.com.agrotis.controller;

import br.com.agrotis.payload.request.PropertyRequest;
import br.com.agrotis.payload.response.PropertyResponse;
import br.com.agrotis.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<PropertyResponse>> list() {
        return ResponseEntity.ok(this.propertyService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> byId(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(this.propertyService.getById(id));
    }

    @PutMapping
    public ResponseEntity<PropertyResponse> update(@RequestBody PropertyResponse response) {
        return ResponseEntity.status(OK).body(this.propertyService.update(response));
    }

    @PostMapping
    public ResponseEntity<PropertyResponse> save(@RequestBody PropertyRequest request) {
        return ResponseEntity.status(CREATED).body(this.propertyService.save(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.propertyService.delete(id);
    }

}
