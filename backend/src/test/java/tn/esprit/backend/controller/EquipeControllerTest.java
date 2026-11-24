package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.dto.EntrepriseDTO;
import tn.esprit.backend.dto.EquipeDTO;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.service.IEquipeService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipeControllerTest {

    @Mock
    private IEquipeService equipeService;

    @InjectMocks
    private EquipeController controller;

    @Test
    void addAndUpdateConvertDtoIncludingEntreprise() {
        EquipeDTO dto = equipeDTO();
        when(equipeService.addEquipe(any(Equipe.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(equipeService.updateEquipe(any(Equipe.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Equipe added = controller.addEquipe(dto);
        Equipe updated = controller.updateEquipe(dto);

        assertEquipeFields(added);
        assertEquipeFields(updated);
        verify(equipeService).addEquipe(argThat(EquipeControllerTest::hasExpectedFields));
        verify(equipeService).updateEquipe(argThat(EquipeControllerTest::hasExpectedFields));
    }

    @Test
    void addEquipeAllowsMissingEntreprise() {
        EquipeDTO dto = new EquipeDTO();
        dto.setNom("Backend");
        when(equipeService.addEquipe(any(Equipe.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Equipe result = controller.addEquipe(dto);

        assertEquals("Backend", result.getNom());
        assertNull(result.getEntreprise());
    }

    @Test
    void delegatesDeleteQueriesAndAssignments() {
        Equipe equipe = new Equipe();
        List<Equipe> equipes = List.of(equipe);
        when(equipeService.getEquipeById(1L)).thenReturn(equipe);
        when(equipeService.getAllEquipes()).thenReturn(equipes);
        when(equipeService.getEquipesByEntreprise(2L)).thenReturn(equipes);
        when(equipeService.assignEquipeToEntreprise(1L, 2L)).thenReturn(equipe);
        when(equipeService.assignEquipeToProjet(1L, 3L)).thenReturn(equipe);

        controller.deleteEquipe(1L);

        assertSame(equipe, controller.getEquipeById(1L));
        assertSame(equipes, controller.getAllEquipes());
        assertSame(equipes, controller.getEquipesByEntreprise(2L));
        assertSame(equipe, controller.assignEquipeToEntreprise(1L, 2L));
        assertSame(equipe, controller.assignEquipeToProjet(1L, 3L));
        verify(equipeService).deleteEquipe(1L);
        verify(equipeService).getEquipeById(1L);
        verify(equipeService).getAllEquipes();
        verify(equipeService).getEquipesByEntreprise(2L);
        verify(equipeService).assignEquipeToEntreprise(1L, 2L);
        verify(equipeService).assignEquipeToProjet(1L, 3L);
    }

    private static EquipeDTO equipeDTO() {
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setId(2L);
        entrepriseDTO.setNom("Esprit");
        entrepriseDTO.setAdresse("Tunis");

        EquipeDTO dto = new EquipeDTO();
        dto.setId(1L);
        dto.setNom("Backend");
        dto.setSpecialite("Java");
        dto.setEntreprise(entrepriseDTO);
        return dto;
    }

    private static boolean hasExpectedFields(Equipe equipe) {
        return equipe.getId().equals(1L)
                && equipe.getNom().equals("Backend")
                && equipe.getSpecialite().equals("Java")
                && equipe.getEntreprise().getId().equals(2L)
                && equipe.getEntreprise().getNom().equals("Esprit")
                && equipe.getEntreprise().getAdresse().equals("Tunis");
    }

    private static void assertEquipeFields(Equipe equipe) {
        assertEquals(1L, equipe.getId());
        assertEquals("Backend", equipe.getNom());
        assertEquals("Java", equipe.getSpecialite());
        assertNotNull(equipe.getEntreprise());
        assertEquals(2L, equipe.getEntreprise().getId());
        assertEquals("Esprit", equipe.getEntreprise().getNom());
        assertEquals("Tunis", equipe.getEntreprise().getAdresse());
    }
}
