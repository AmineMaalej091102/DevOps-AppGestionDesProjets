package tn.esprit.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class EntrepriseDTO {

    private Long id;

    private String nom;

    private String adresse;

    @JsonIgnore
    private List<EquipeDTO> equipes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<EquipeDTO> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<EquipeDTO> equipes) {
        this.equipes = equipes;
    }
}
