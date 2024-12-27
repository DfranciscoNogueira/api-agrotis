package br.com.agrotis.service;

import br.com.agrotis.exception.AlreadyUsedException;
import br.com.agrotis.exception.NoSuchElementFoundException;
import br.com.agrotis.model.Laboratory;
import br.com.agrotis.model.Property;
import br.com.agrotis.payload.request.LaboratoryRequest;
import br.com.agrotis.payload.request.PropertyRequest;
import br.com.agrotis.payload.response.LaboratoryResponse;
import br.com.agrotis.payload.response.PropertyResponse;
import br.com.agrotis.repository.LaboratoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
class LaboratoryServiceTest {

    @InjectMocks
    private LaboratoryService service;

    @Mock
    private LaboratoryRepository repository;

    @Mock
    private ModelMapper mapper;

    @Test
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(expectedModel()));
        var actualList = service.listAll();
        LaboratoryResponse expectedModel = this.mapper.map(expectedModel(), LaboratoryResponse.class);
        assertEquals(1, actualList.size());
        assertEquals(expectedModel, actualList.get(0));
    }

    @Test
    void getById() {
        when(repository.findById(any())).thenReturn(Optional.of(expectedModel()));
        LaboratoryResponse expectedModel = this.mapper.map(expectedModel(), LaboratoryResponse.class);
        var actual = service.getById(1L);
        assertEquals(expectedModel, actual);
    }

    @Test
    void getByIdNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        var actual = service.getById(1L);
        assertNull(actual);
    }

    @Test
    void save() {
        when(repository.existsByNameIgnoreCase(any())).thenReturn(false);
        when(mapper.map(any(LaboratoryRequest.class), any())).thenReturn(expectedModel());
        when(mapper.map(any(Laboratory.class), any())).thenReturn(expectedResponse());
        when(repository.save(any(Laboratory.class))).thenReturn(expectedModel());
        var actual = service.save(expectedRequest());
        assertEquals(expectedResponse().getId(), actual.getId());
        verify(repository).existsByNameIgnoreCase(any());
        verify(repository).save(any());
    }

    @Test
    void update() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.existsByNameIgnoreCase(any())).thenReturn(false);
        when(mapper.map(any(LaboratoryResponse.class), any())).thenReturn(expectedModel());
        when(mapper.map(any(Laboratory.class), any())).thenReturn(expectedResponse());
        when(repository.save(any(Laboratory.class))).thenReturn(expectedModel());
        var actual = service.update(expectedResponse());
        assertEquals(expectedResponse().getId(), actual.getId());
        verify(repository).existsById(1L);
        verify(repository).save(any());
    }

    @Test
    void updateExistName() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.existsByNameIgnoreCase(any())).thenReturn(true);
        AlreadyUsedException exception = assertThrows(AlreadyUsedException.class, () -> service.update(expectedResponse()));
        assertTrue(exception.getMessage().matches("409 CONFLICT \"Nome = MOCK já existe\""));
    }

    @Test
    void updateNotExist() {
        when(repository.existsById(1L)).thenReturn(false);
        NoSuchElementFoundException exception = assertThrows(NoSuchElementFoundException.class, () -> service.update(expectedResponse()));
        assertTrue(exception.getMessage().matches("404 NOT_FOUND \"Laboratorio com ID = 1 não encontrado\""));
    }

    @Test
    void deleteSuccessfully() {
        doAnswer(i -> null).when(repository).deleteById(any());
        service.delete(1L);
        verify(repository).deleteById(1L);
    }

    private Laboratory expectedModel() {
        return new Laboratory(1L, "MOCK", Collections.emptyList());
    }

    private LaboratoryResponse expectedResponse() {
        return new LaboratoryResponse(1L, "MOCK");
    }

    private LaboratoryRequest expectedRequest() {
        return new LaboratoryRequest("MOCK");
    }

}