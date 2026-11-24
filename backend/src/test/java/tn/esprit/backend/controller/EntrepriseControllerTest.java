package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.dto.EntrepriseDTO;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.service.IEntrepriseService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrepriseControllerTest {

    @Mock
    private IEntrepriseService entrepriseService;

    @InjectMocks
    private EntrepriseController controller;

    @Test
    void addAndUpdateConvertDtoToEntity() {
        EntrepriseDTO dto = new EntrepriseDTO();
        dto.setId(1L);
        dto.setNom("Esprit");
        dto.setAdresse("Tunis");
        when(entrepriseService.addEntreprise(any(Entreprise.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(entrepriseService.updateEntreprise(any(Entreprise.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Entreprise added = controller.addEntreprise(dto);
        Entreprise updated = controller.updateEntreprise(dto);

        assertEntrepriseFields(added);
        assertEntrepriseFields(updated);
        verify(entrepriseService).addEntreprise(argThat(EntrepriseControllerTest::hasExpectedFields));
        verify(entrepriseService).updateEntreprise(argThat(EntrepriseControllerTest::hasExpectedFields));
    }

    @Test
    void delegatesDeleteAndQueries() {
        Entreprise entreprise = new Entreprise();
        List<Entreprise> entreprises = List.of(entreprise);
        when(entrepriseService.getEntrepriseById(2L)).thenReturn(entreprise);
        when(entrepriseService.getAllEntreprises()).thenReturn(entreprises);

        controller.deleteEntreprise(2L);

        assertSame(entreprise, controller.getEntrepriseById(2L));
        assertSame(entreprises, controller.getAllEntreprises());
        verify(entrepriseService).deleteEntreprise(2L);
        verify(entrepriseService).getEntrepriseById(2L);
        verify(entrepriseService).getAllEntreprises();
    }

    private static boolean hasExpectedFields(Entreprise entreprise) {
        return entreprise.getId().equals(1L)
                && entreprise.getNom().equals("Esprit")
                && entreprise.getAdresse().equals("Tunis");
    }

    private static void assertEntrepriseFields(Entreprise entreprise) {
        assertEquals(1L, entreprise.getId());
        assertEquals("Esprit", entreprise.getNom());
        assertEquals("Tunis", entreprise.getAdresse());
    }
}
