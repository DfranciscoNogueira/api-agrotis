package br.com.agrotis.mapper;

import br.com.agrotis.model.Laboratory;
import br.com.agrotis.payload.response.LaboratoryFilterResponse;

import java.util.List;
import java.util.stream.Collectors;

public class LaboratoryMapper {

    public static LaboratoryFilterResponse toFilterResponse(Laboratory model) {
        return new LaboratoryFilterResponse(model.getId(), model.getName(), model.getPeople().size());
    }

    public static List<LaboratoryFilterResponse> toListFilterResponse(List<Laboratory> listModel) {
        return listModel.stream().map(LaboratoryMapper::toFilterResponse).collect(Collectors.toList());
    }

}
