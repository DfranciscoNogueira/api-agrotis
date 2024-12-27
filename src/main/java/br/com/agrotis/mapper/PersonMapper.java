package br.com.agrotis.mapper;

import br.com.agrotis.model.Laboratory;
import br.com.agrotis.model.Person;
import br.com.agrotis.model.Property;
import br.com.agrotis.payload.request.PersonRequest;

public class PersonMapper {

    public static Person toModel(PersonRequest request) {
        return new Person(null,
                request.getName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getObservations(),
                new Property(request.getPropertyId()),
                new Laboratory(request.getLaboratoryId()));
    }

}
