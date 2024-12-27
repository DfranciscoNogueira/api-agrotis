package br.com.agrotis.repository;

import br.com.agrotis.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Long>, JpaSpecificationExecutor<Laboratory> {

    boolean existsByNameIgnoreCase(String name);

}
