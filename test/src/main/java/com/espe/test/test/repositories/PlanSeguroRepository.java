package com.espe.test.test.repositories; // <--- Verifica este paquete

import com.espe.test.test.models.entities.PlanSeguro; // <--- Verifica este import
import org.springframework.data.repository.CrudRepository;

public interface PlanSeguroRepository extends CrudRepository<PlanSeguro, Long> {
}