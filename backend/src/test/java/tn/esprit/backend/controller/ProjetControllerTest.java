package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.dto.ProjetDTO;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.service.IProjetService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetControllerTest {

    @Mock
    private IProjetService projetService;

    @InjectMocks
    private ProjetController controller;

    @Test
    void addAndUpdateConvertDtoToEntity() {
        ProjetDTO dto = new ProjetDTO();
        dto.setId(1L);
        dto.setSujet("Migration");
        when(projetService.addProjet(any(Projet.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(projetService.updateProjet(any(Projet.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Projet added = controller.addProjet(dto);
        Projet updated = controller.updateProjet(dto);

        assertProjetFields(added);
        assertProjetFields(updated);
        verify(projetService).addProjet(argThat(ProjetControllerTest::hasExpectedFields));
        verify(projetService).updateProjet(argThat(ProjetControllerTest::hasExpectedFields));
    }

    @Test
    void delegatesDeleteAndQueries() {
        Projet projet = new Projet();
        List<Projet> projets = List.of(projet);
        when(projetService.getProjetById(2L)).thenReturn(projet);
        when(projetService.getAllProjets()).thenReturn(projets);

        controller.deleteProjet(2L);

        assertSame(projet, controller.getProjetById(2L));
        assertSame(projets, controller.getAllProjets());
        verify(projetService).deleteProjet(2L);
        verify(projetService).getProjetById(2L);
        verify(projetService).getAllProjets();
    }

    private static boolean hasExpectedFields(Projet projet) {
        return projet.getId().equals(1L) && projet.getSujet().equals("Migration");
    }

    private static void assertProjetFields(Projet projet) {
        assertEquals(1L, projet.getId());
        assertEquals("Migration", projet.getSujet());
    }
}
