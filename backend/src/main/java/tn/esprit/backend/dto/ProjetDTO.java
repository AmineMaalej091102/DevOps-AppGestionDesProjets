package tn.esprit.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjetDTO {

    private Long id;

    private String sujet;

    @JsonIgnore
    private List<EquipeDTO> equipes;

    @JsonIgnore
    private List<ProjetDetailleDTO> projetsDetailles;
}
