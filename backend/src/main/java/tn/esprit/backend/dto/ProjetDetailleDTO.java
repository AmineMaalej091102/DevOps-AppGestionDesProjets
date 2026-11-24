package tn.esprit.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProjetDetailleDTO {

    private Long id;

    private String description;

    private String technologie;

    private Double coutProvisoire;

    private LocalDate dateDebut;

    @JsonIgnore
    private ProjetDTO projet;
}
