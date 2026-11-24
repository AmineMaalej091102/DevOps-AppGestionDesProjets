package tn.esprit.backend.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void entrepriseDtoExposesAllFields() {
        EquipeDTO equipe = new EquipeDTO();
        List<EquipeDTO> equipes = List.of(equipe);
        EntrepriseDTO dto = new EntrepriseDTO();
        dto.setId(1L);
        dto.setNom("Esprit");
        dto.setAdresse("Tunis");
        dto.setEquipes(equipes);

        assertEquals(1L, dto.getId());
        assertEquals("Esprit", dto.getNom());
        assertEquals("Tunis", dto.getAdresse());
        assertSame(equipes, dto.getEquipes());
    }

    @Test
    void equipeDtoExposesAllFields() {
        EntrepriseDTO entreprise = new EntrepriseDTO();
        ProjetDTO projet = new ProjetDTO();
        List<ProjetDTO> projets = List.of(projet);
        EquipeDTO dto = new EquipeDTO();
        dto.setId(2L);
        dto.setNom("Backend");
        dto.setSpecialite("Java");
        dto.setEntreprise(entreprise);
        dto.setProjets(projets);

        assertEquals(2L, dto.getId());
        assertEquals("Backend", dto.getNom());
        assertEquals("Java", dto.getSpecialite());
        assertSame(entreprise, dto.getEntreprise());
        assertSame(projets, dto.getProjets());
    }

    @Test
    void projetDtoExposesAllFields() {
        EquipeDTO equipe = new EquipeDTO();
        ProjetDetailleDTO projetDetaille = new ProjetDetailleDTO();
        List<EquipeDTO> equipes = List.of(equipe);
        List<ProjetDetailleDTO> projetsDetailles = List.of(projetDetaille);
        ProjetDTO dto = new ProjetDTO();
        dto.setId(3L);
        dto.setSujet("Migration");
        dto.setEquipes(equipes);
        dto.setProjetsDetailles(projetsDetailles);

        assertEquals(3L, dto.getId());
        assertEquals("Migration", dto.getSujet());
        assertSame(equipes, dto.getEquipes());
        assertSame(projetsDetailles, dto.getProjetsDetailles());
    }

    @Test
    void projetDetailleDtoExposesAllFields() {
        ProjetDTO projet = new ProjetDTO();
        ProjetDetailleDTO dto = new ProjetDetailleDTO();
        LocalDate dateDebut = LocalDate.of(2026, 1, 15);
        dto.setId(4L);
        dto.setDescription("API");
        dto.setTechnologie("Spring");
        dto.setCoutProvisoire(1200.5);
        dto.setDateDebut(dateDebut);
        dto.setProjet(projet);

        assertEquals(4L, dto.getId());
        assertEquals("API", dto.getDescription());
        assertEquals("Spring", dto.getTechnologie());
        assertEquals(1200.5, dto.getCoutProvisoire());
        assertSame(dateDebut, dto.getDateDebut());
        assertSame(projet, dto.getProjet());
    }
}
