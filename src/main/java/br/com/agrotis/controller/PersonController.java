package br.com.agrotis.controller;

import br.com.agrotis.payload.request.PersonRequest;
import br.com.agrotis.payload.response.PersonResponse;
import br.com.agrotis.service.PersonService;
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
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> list() {
        return ResponseEntity.ok(this.personService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> byId(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(this.personService.getById(id));
    }

    @PutMapping
    public ResponseEntity<PersonResponse> update(@RequestBody PersonResponse response) {
        return ResponseEntity.status(OK).body(this.personService.update(response));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody PersonRequest request) {
        return ResponseEntity.status(CREATED).body(this.personService.save(request).getId());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.personService.delete(id);
    }

}
