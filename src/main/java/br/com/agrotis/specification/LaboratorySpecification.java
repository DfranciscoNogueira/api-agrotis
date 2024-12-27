package br.com.agrotis.specification;

import br.com.agrotis.model.Laboratory;
import br.com.agrotis.model.Person;
import br.com.agrotis.payload.request.LaboratoryFilterRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LaboratorySpecification {
    public static Specification<Laboratory> filterBy(LaboratoryFilterRequest filter) {
        return new Specification<Laboratory>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Laboratory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                Join<Person, Laboratory> join = root.join("people");

                Predicate predicateSize = criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.size(root.get("people")), filter.getQtdMinPeople());
                predicates.add(predicateSize);

                if (Objects.nonNull(filter.getObservation())) {
                    Predicate predicate = criteriaBuilder.like(join.get("observations"), "%" + filter.getObservation() + "%");
                    predicates.add(predicate);
                }

                if (Objects.nonNull(filter.getStartDate())) {
                    Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(join.get("startDate"), filter.getStartDate());
                    predicates.add(predicate);
                }

                if (Objects.nonNull(filter.getEndDate())) {
                    Predicate predicate = criteriaBuilder.lessThanOrEqualTo(join.get("endDate"), filter.getEndDate());
                    predicates.add(predicate);
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        };

    }

}
