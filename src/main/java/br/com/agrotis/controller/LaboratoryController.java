package br.com.agrotis.controller;

import br.com.agrotis.payload.request.LaboratoryFilterRequest;
import br.com.agrotis.payload.request.LaboratoryRequest;
import br.com.agrotis.payload.response.LaboratoryFilterResponse;
import br.com.agrotis.payload.response.LaboratoryResponse;
import br.com.agrotis.service.LaboratoryService;
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
@RequestMapping("/laboratories")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    @Autowired
    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @GetMapping
    public ResponseEntity<List<LaboratoryResponse>> list() {
        return ResponseEntity.ok(this.laboratoryService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratoryResponse> byId(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(this.laboratoryService.getById(id));
    }

    @PutMapping
    public ResponseEntity<LaboratoryResponse> update(@RequestBody LaboratoryResponse response) {
        return ResponseEntity.status(OK).body(this.laboratoryService.update(response));
    }

    @PostMapping
    public ResponseEntity<LaboratoryResponse> save(@RequestBody LaboratoryRequest request) {
        return ResponseEntity.status(CREATED).body(this.laboratoryService.save(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.laboratoryService.delete(id);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<LaboratoryFilterResponse>> getLaboratoriesByFilter(@RequestBody LaboratoryFilterRequest filter) {
        return ResponseEntity.ok().body(this.laboratoryService.getByFilter(filter));
    }

}
