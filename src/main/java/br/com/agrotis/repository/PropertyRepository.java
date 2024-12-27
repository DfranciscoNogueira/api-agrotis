package br.com.agrotis.repository;

import br.com.agrotis.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    boolean existsByNameIgnoreCase(String name);

}
