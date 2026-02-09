package com.espe.test.test.repositories; // <--- Verifica este paquete

import com.espe.test.test.models.entities.PlanSeguro; // <--- Verifica este import
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanSeguroRepository extends JpaRepository<PlanSeguro, Integer> {
}