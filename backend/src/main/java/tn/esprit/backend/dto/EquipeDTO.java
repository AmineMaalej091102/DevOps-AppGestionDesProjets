package tn.esprit.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EquipeDTO {

    private Long id;

    private String nom;

    private String specialite;

    private EntrepriseDTO entreprise;

    @JsonIgnore
    private List<ProjetDTO> projets;
}
