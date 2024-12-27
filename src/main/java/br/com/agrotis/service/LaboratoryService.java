package br.com.agrotis.service;

import br.com.agrotis.enums.ErrorMessages;
import br.com.agrotis.exception.AlreadyUsedException;
import br.com.agrotis.exception.NoSuchElementFoundException;
import br.com.agrotis.mapper.LaboratoryMapper;
import br.com.agrotis.model.Laboratory;
import br.com.agrotis.payload.request.LaboratoryFilterRequest;
import br.com.agrotis.payload.request.LaboratoryRequest;
import br.com.agrotis.payload.response.LaboratoryFilterResponse;
import br.com.agrotis.payload.response.LaboratoryResponse;
import br.com.agrotis.repository.LaboratoryRepository;
import br.com.agrotis.specification.LaboratorySpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LaboratoryService {

    private final LaboratoryRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    public LaboratoryService(LaboratoryRepository repository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    public List<LaboratoryResponse> listAll() {
        List<Laboratory> laboratories = this.repository.findAll();
        return laboratories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public LaboratoryResponse getById(Long id) {
        Optional<Laboratory> entity = this.repository.findById(id);
        return entity.map(laboratory -> this.modelMapper.map(laboratory, LaboratoryResponse.class)).orElse(null);
    }

    public LaboratoryResponse save(LaboratoryRequest laboratory) {
        this.validateExistByName(laboratory.getName());
        Laboratory request = this.modelMapper.map(laboratory, Laboratory.class);
        Laboratory entitySave = this.repository.save(request);
        return this.modelMapper.map(entitySave, LaboratoryResponse.class);
    }

    public LaboratoryResponse update(LaboratoryResponse laboratory) {
        this.validateExist(laboratory.getId());
        this.validateExistByName(laboratory.getName());
        Laboratory request = this.modelMapper.map(laboratory, Laboratory.class);
        Laboratory entitySave = this.repository.save(request);
        return this.modelMapper.map(entitySave, LaboratoryResponse.class);
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    public List<LaboratoryFilterResponse> getByFilter(LaboratoryFilterRequest filter) {
        this.validateFilter(filter);
        List<Laboratory> list = this.repository.findAll(LaboratorySpecification.filterBy(filter));
        return LaboratoryMapper.toListFilterResponse(list);
    }

    private LaboratoryResponse convertToDto(Laboratory laboratory) {
        return this.modelMapper.map(laboratory, LaboratoryResponse.class);
    }

    private void validateExistByName(String name) {
        if (Objects.nonNull(name) && this.repository.existsByNameIgnoreCase(name)) {
            throw new AlreadyUsedException(ErrorMessages.ALREADY_USED_KEY.format("Nome", name));
        }
    }

    private void validateExist(Long id) {
        if (Objects.nonNull(id) && !this.repository.existsById(id))
            throw new NoSuchElementFoundException(ErrorMessages.ENTITY_NOT_FOUND_GENERIC.format("Laboratorio", "ID", id));
    }

    private void validateFilter(LaboratoryFilterRequest filter) {
        if (Objects.isNull(filter.getQtdMinPeople()))
            throw new IllegalArgumentException(ErrorMessages.INVALID_INPUT.format("quantidade_minima"));
    }

}
