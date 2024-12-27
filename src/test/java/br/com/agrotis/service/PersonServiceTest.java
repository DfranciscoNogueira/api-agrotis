package br.com.agrotis.service;

import br.com.agrotis.exception.NoSuchElementFoundException;
import br.com.agrotis.model.Laboratory;
import br.com.agrotis.model.Person;
import br.com.agrotis.model.Property;
import br.com.agrotis.payload.request.LaboratoryRequest;
import br.com.agrotis.payload.request.PersonRequest;
import br.com.agrotis.payload.response.LaboratoryResponse;
import br.com.agrotis.payload.response.PersonResponse;
import br.com.agrotis.payload.response.PropertyResponse;
import br.com.agrotis.repository.LaboratoryRepository;
import br.com.agrotis.repository.PersonRepository;
import br.com.agrotis.repository.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService service;

    @Mock
    private LaboratoryRepository laboratoryRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void listAll() {
        when(personRepository.findAll()).thenReturn(List.of(expectedModel()));
        var actualList = service.listAll();
        PersonResponse expectedModel = this.mapper.map(expectedModel(), PersonResponse.class);
        assertEquals(1, actualList.size());
        assertEquals(expectedModel, actualList.get(0));
    }

    @Test
    void getById() {
        when(personRepository.findById(any())).thenReturn(Optional.of(expectedModel()));
        PersonResponse expectedModel = this.mapper.map(expectedModel(), PersonResponse.class);
        var actual = service.getById(1L);
        assertEquals(expectedModel, actual);
    }

    @Test
    void getByIdNotFound() {
        when(personRepository.findById(any())).thenReturn(Optional.empty());
        var actual = service.getById(1L);
        assertNull(actual);
    }

    @Test
    void save() {
        when(laboratoryRepository.existsById(any())).thenReturn(true);
        when(propertyRepository.existsById(any())).thenReturn(true);
        when(mapper.map(any(Person.class), any())).thenReturn(expectedResponse());
        when(personRepository.save(any(Person.class))).thenReturn(expectedModel());
        var actual = service.save(expectedRequest());
        assertEquals(expectedResponse().getId(), actual.getId());
        verify(laboratoryRepository).existsById(any());
        verify(propertyRepository).existsById(any());
        verify(personRepository).save(any());
    }

    @Test
    void update() {
        when(personRepository.existsById(1L)).thenReturn(true);
        when(laboratoryRepository.existsById(any())).thenReturn(true);
        when(propertyRepository.existsById(any())).thenReturn(true);
        when(mapper.map(any(PersonResponse.class), any())).thenReturn(expectedModel());
        when(mapper.map(any(Person.class), any())).thenReturn(expectedResponse());
        when(personRepository.save(any(Person.class))).thenReturn(expectedModel());
        var actual = service.update(expectedResponse());
        assertEquals(expectedResponse().getId(), actual.getId());
        verify(personRepository).existsById(1L);
        verify(personRepository).save(any());
    }

    @Test
    void updateNotExistLaboratory() {
        when(personRepository.existsById(1L)).thenReturn(true);
        when(laboratoryRepository.existsById(any())).thenReturn(false);
        NoSuchElementFoundException exception = assertThrows(NoSuchElementFoundException.class, () -> service.update(expectedResponse()));
        assertTrue(exception.getMessage().matches("404 NOT_FOUND \"Laboratorio com ID = 1 não encontrado\""));
    }

    @Test
    void updateNotExistProperty() {
        when(personRepository.existsById(1L)).thenReturn(true);
        when(laboratoryRepository.existsById(any())).thenReturn(true);
        when(propertyRepository.existsById(any())).thenReturn(false);
        NoSuchElementFoundException exception = assertThrows(NoSuchElementFoundException.class, () -> service.update(expectedResponse()));
        assertTrue(exception.getMessage().matches("404 NOT_FOUND \"Propriedade com ID = 1 não encontrado\""));
    }

    @Test
    void deleteSuccessfully() {
        doAnswer(i -> null).when(personRepository).deleteById(any());
        service.delete(1L);
        verify(personRepository).deleteById(1L);
    }

    private Person expectedModel() {
        return new Person(1L, "MOCK", LocalDateTime.now(), LocalDateTime.now(), "MOCK", new Property(1L, "MOCK"), new Laboratory(1L, "MOCK", Collections.emptyList()));
    }

    private PersonResponse expectedResponse() {
        return new PersonResponse(1L, "MOCK", LocalDateTime.now(), LocalDateTime.now(), "MOCK", new PropertyResponse(1L, "MOCK"), new LaboratoryResponse(1L, "MOCK"));
    }

    private PersonRequest expectedRequest() {
        return new PersonRequest("MOCK", LocalDateTime.now(), LocalDateTime.now(), "MOCK", 1L, 1L);
    }

}