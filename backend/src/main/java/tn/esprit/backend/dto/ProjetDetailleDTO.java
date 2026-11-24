package tn.esprit.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class ProjetDetailleDTO {

    private Long id;

    private String description;

    private String technologie;

    private Double coutProvisoire;

    private LocalDate dateDebut;

    @JsonIgnore
    private ProjetDTO projet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnologie() {
        return technologie;
    }

    public void setTechnologie(String technologie) {
        this.technologie = technologie;
    }

    public Double getCoutProvisoire() {
        return coutProvisoire;
    }

    public void setCoutProvisoire(Double coutProvisoire) {
        this.coutProvisoire = coutProvisoire;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ProjetDTO getProjet() {
        return projet;
    }

    public void setProjet(ProjetDTO projet) {
        this.projet = projet;
    }
}
