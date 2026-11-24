package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.dto.ProjetDetailleDTO;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.service.IProjetDetailleService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetDetailleControllerTest {

    @Mock
    private IProjetDetailleService projetDetailleService;

    @InjectMocks
    private ProjetDetailleController controller;

    @Test
    void addAndUpdateConvertDtoToEntity() {
        ProjetDetailleDTO dto = projetDetailleDTO();
        when(projetDetailleService.addProjetDetaille(any(ProjetDetaille.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(projetDetailleService.updateProjetDetaille(any(ProjetDetaille.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProjetDetaille added = controller.addProjetDetaille(dto);
        ProjetDetaille updated = controller.updateProjetDetaille(dto);

        assertProjetDetailleFields(added);
        assertProjetDetailleFields(updated);
        verify(projetDetailleService).addProjetDetaille(argThat(ProjetDetailleControllerTest::hasExpectedFields));
        verify(projetDetailleService).updateProjetDetaille(argThat(ProjetDetailleControllerTest::hasExpectedFields));
    }

    @Test
    void delegatesDeleteQueriesAndAssignment() {
        ProjetDetaille projetDetaille = new ProjetDetaille();
        List<ProjetDetaille> projetsDetailles = List.of(projetDetaille);
        when(projetDetailleService.getProjetDetailleById(1L)).thenReturn(projetDetaille);
        when(projetDetailleService.getAllProjetsDetailles()).thenReturn(projetsDetailles);
        when(projetDetailleService.getProjetDetaillesByProjet(2L)).thenReturn(projetsDetailles);
        when(projetDetailleService.assignProjetDetailleToProjet(1L, 2L)).thenReturn(projetDetaille);

        controller.deleteProjetDetaille(1L);

        assertSame(projetDetaille, controller.getProjetDetailleById(1L));
        assertSame(projetsDetailles, controller.getAllProjetsDetailles());
        assertSame(projetsDetailles, controller.getProjetDetaillesByProjet(2L));
        assertSame(projetDetaille, controller.assignProjetDetailleToProjet(1L, 2L));
        verify(projetDetailleService).deleteProjetDetaille(1L);
        verify(projetDetailleService).getProjetDetailleById(1L);
        verify(projetDetailleService).getAllProjetsDetailles();
        verify(projetDetailleService).getProjetDetaillesByProjet(2L);
        verify(projetDetailleService).assignProjetDetailleToProjet(1L, 2L);
    }

    private static ProjetDetailleDTO projetDetailleDTO() {
        ProjetDetailleDTO dto = new ProjetDetailleDTO();
        dto.setId(1L);
        dto.setDescription("API");
        dto.setTechnologie("Spring");
        dto.setCoutProvisoire(1200.5);
        dto.setDateDebut(LocalDate.of(2026, 1, 15));
        return dto;
    }

    private static boolean hasExpectedFields(ProjetDetaille projetDetaille) {
        return projetDetaille.getId().equals(1L)
                && projetDetaille.getDescription().equals("API")
                && projetDetaille.getTechnologie().equals("Spring")
                && projetDetaille.getCoutProvisoire().equals(1200.5)
                && projetDetaille.getDateDebut().equals(LocalDate.of(2026, 1, 15));
    }

    private static void assertProjetDetailleFields(ProjetDetaille projetDetaille) {
        assertEquals(1L, projetDetaille.getId());
        assertEquals("API", projetDetaille.getDescription());
        assertEquals("Spring", projetDetaille.getTechnologie());
        assertEquals(1200.5, projetDetaille.getCoutProvisoire());
        assertEquals(LocalDate.of(2026, 1, 15), projetDetaille.getDateDebut());
    }
}
