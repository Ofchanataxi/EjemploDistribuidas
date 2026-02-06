package ec.edu.espe.polizams.models.dto;

import java.math.BigDecimal;

public class PlanDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private BigDecimal primaBase;
    private BigDecimal coberturaMax;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}