package br.com.agrotis.service;

import br.com.agrotis.enums.ErrorMessages;
import br.com.agrotis.exception.NoSuchElementFoundException;
import br.com.agrotis.mapper.PersonMapper;
import br.com.agrotis.model.Person;
import br.com.agrotis.payload.request.PersonRequest;
import br.com.agrotis.payload.response.PersonResponse;
import br.com.agrotis.repository.LaboratoryRepository;
import br.com.agrotis.repository.PersonRepository;
import br.com.agrotis.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final LaboratoryRepository laboratoryRepository;

    private final PropertyRepository propertyRepository;

    private final PersonRepository personRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PersonService(LaboratoryRepository laboratoryRepository, PropertyRepository propertyRepository, PersonRepository personRepository, ModelMapper modelMapper) {
        this.laboratoryRepository = laboratoryRepository;
        this.propertyRepository = propertyRepository;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    public List<PersonResponse> listAll() {
        List<Person> people = this.personRepository.findAll();
        return people.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PersonResponse getById(Long id) {
        Optional<Person> entity = this.personRepository.findById(id);
        return entity.map(person -> this.modelMapper.map(person, PersonResponse.class)).orElse(null);
    }

    public PersonResponse save(PersonRequest person) {
        this.validateExistLaboratory(person.getLaboratoryId());
        this.validateExistProperty(person.getPropertyId());
        Person request = PersonMapper.toModel(person);
        Person entitySave = this.personRepository.save(request);
        return this.modelMapper.map(entitySave, PersonResponse.class);
    }

    public PersonResponse update(PersonResponse person) {
        this.validateExistPerson(person.getId());
        this.validateExistLaboratory(Objects.isNull(person.getLaboratory()) ? null : person.getLaboratory().getId());
        this.validateExistProperty(Objects.isNull(person.getProperty()) ? null : person.getProperty().getId());
        Person request = this.modelMapper.map(person, Person.class);
        Person entitySave = this.personRepository.save(request);
        return this.modelMapper.map(entitySave, PersonResponse.class);
    }

    public void delete(Long id) {
        this.personRepository.deleteById(id);
    }

    private PersonResponse convertToDto(Person person) {
        return this.modelMapper.map(person, PersonResponse.class);
    }

    private void validateExistPerson(Long idPerson) {
        if (Objects.nonNull(idPerson) && !this.personRepository.existsById(idPerson))
            throw new NoSuchElementFoundException(ErrorMessages.ENTITY_NOT_FOUND_GENERIC.format("Pessoa", "ID", idPerson));
    }

    private void validateExistLaboratory(Long idLaboratory) {
        if (Objects.nonNull(idLaboratory) && !this.laboratoryRepository.existsById(idLaboratory))
            throw new NoSuchElementFoundException(ErrorMessages.ENTITY_NOT_FOUND_GENERIC.format("Laboratorio", "ID", idLaboratory));
    }

    private void validateExistProperty(Long idProperty) {
        if (Objects.nonNull(idProperty) && !this.propertyRepository.existsById(idProperty))
            throw new NoSuchElementFoundException(ErrorMessages.ENTITY_NOT_FOUND_GENERIC.format("Propriedade", "ID", idProperty));
    }

}
