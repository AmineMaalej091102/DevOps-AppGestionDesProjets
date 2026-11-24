package tn.esprit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.ProjetDTO;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.service.IProjetService;

import java.util.List;

@RestController
@RequestMapping("/projet")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {

    IProjetService projetService;

    @PostMapping("/add")
    public Projet addProjet(@RequestBody ProjetDTO projetDTO) {
        return projetService.addProjet(toProjet(projetDTO));
    }

    @PutMapping("/update")
    public Projet updateProjet(@RequestBody ProjetDTO projetDTO) {
        return projetService.updateProjet(toProjet(projetDTO));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
    }

    @GetMapping("/get/{id}")
    public Projet getProjetById(@PathVariable Long id) {
        return projetService.getProjetById(id);
    }

    @GetMapping("/all")
    public List<Projet> getAllProjets() {
        return projetService.getAllProjets();
    }

    private Projet toProjet(ProjetDTO projetDTO) {
        Projet projet = new Projet();
        projet.setId(projetDTO.getId());
        projet.setSujet(projetDTO.getSujet());
        return projet;
    }
}
