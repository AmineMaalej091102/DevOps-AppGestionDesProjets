package tn.esprit.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class ProjetDTO {

    private Long id;

    private String sujet;

    @JsonIgnore
    private List<EquipeDTO> equipes;

    @JsonIgnore
    private List<ProjetDetailleDTO> projetsDetailles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public List<EquipeDTO> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<EquipeDTO> equipes) {
        this.equipes = equipes;
    }

    public List<ProjetDetailleDTO> getProjetsDetailles() {
        return projetsDetailles;
    }

    public void setProjetsDetailles(List<ProjetDetailleDTO> projetsDetailles) {
        this.projetsDetailles = projetsDetailles;
    }
}
