package tn.esprit.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class EquipeDTO {

    private Long id;

    private String nom;

    private String specialite;

    private EntrepriseDTO entreprise;

    @JsonIgnore
    private List<ProjetDTO> projets;

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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public EntrepriseDTO getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseDTO entreprise) {
        this.entreprise = entreprise;
    }

    public List<ProjetDTO> getProjets() {
        return projets;
    }

    public void setProjets(List<ProjetDTO> projets) {
        this.projets = projets;
    }
}
