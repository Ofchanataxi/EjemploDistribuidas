package ec.edu.espe.polina.models.dto;

public class ClienteDTO {
    private Long id;
    private String nombres;
    private String identificacion;
    private String email;
    private String telefono;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    // Otros getters/setters omitidos por brevedad si no se usan logicamente
}