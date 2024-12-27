package br.com.agrotis.service;

import br.com.agrotis.enums.ErrorMessages;
import br.com.agrotis.exception.AlreadyUsedException;
import br.com.agrotis.exception.NoSuchElementFoundException;
import br.com.agrotis.model.Property;
import br.com.agrotis.payload.request.PropertyRequest;
import br.com.agrotis.payload.response.PropertyResponse;
import br.com.agrotis.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    public PropertyService(PropertyRepository repository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    public List<PropertyResponse> listAll() {
        List<Property> properties = this.repository.findAll();
        return properties.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PropertyResponse getById(Long id) {
        Optional<Property> entity = this.repository.findById(id);
        return entity.map(property -> this.modelMapper.map(property, PropertyResponse.class)).orElse(null);
    }

    public PropertyResponse save(PropertyRequest property) {
        this.validateExistByName(property.getName());
        Property request = this.modelMapper.map(property, Property.class);
        Property entitySave = this.repository.save(request);
        return this.modelMapper.map(entitySave, PropertyResponse.class);
    }

    public PropertyResponse update(PropertyResponse property) {
        this.validateExist(property.getId());
        this.validateExistByName(property.getName());
        Property request = this.modelMapper.map(property, Property.class);
        Property entitySave = this.repository.save(request);
        return this.modelMapper.map(entitySave, PropertyResponse.class);
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    private PropertyResponse convertToDto(Property property) {
        return this.modelMapper.map(property, PropertyResponse.class);
    }

    private void validateExistByName(String name) {
        if (Objects.nonNull(name) && this.repository.existsByNameIgnoreCase(name)) {
            throw new AlreadyUsedException(ErrorMessages.ALREADY_USED_KEY.format("Nome", name));
        }
    }

    private void validateExist(Long id) {
        if (Objects.nonNull(id) && !this.repository.existsById(id))
            throw new NoSuchElementFoundException(ErrorMessages.ENTITY_NOT_FOUND_GENERIC.format("Propriedade", "ID", id));
    }

}
