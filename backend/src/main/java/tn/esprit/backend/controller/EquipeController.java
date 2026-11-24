package tn.esprit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.EntrepriseDTO;
import tn.esprit.backend.dto.EquipeDTO;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.service.IEquipeService;

import java.util.List;

@RestController
@RequestMapping("/equipe")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EquipeController {

    IEquipeService equipeService;

    @PostMapping("/add")
    public Equipe addEquipe(@RequestBody EquipeDTO equipeDTO) {
        return equipeService.addEquipe(toEquipe(equipeDTO));
    }

    @PutMapping("/update")
    public Equipe updateEquipe(@RequestBody EquipeDTO equipeDTO) {
        return equipeService.updateEquipe(toEquipe(equipeDTO));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEquipe(@PathVariable Long id) {
        equipeService.deleteEquipe(id);
    }

    @GetMapping("/get/{id}")
    public Equipe getEquipeById(@PathVariable Long id) {
        return equipeService.getEquipeById(id);
    }

    @GetMapping("/all")
    public List<Equipe> getAllEquipes() {
        return equipeService.getAllEquipes();
    }

    @GetMapping("/by-entreprise/{entrepriseId}")
    public List<Equipe> getEquipesByEntreprise(@PathVariable Long entrepriseId) {
        return equipeService.getEquipesByEntreprise(entrepriseId);
    }

    @PutMapping("/assign-entreprise/{equipeId}/{entrepriseId}")
    public Equipe assignEquipeToEntreprise(@PathVariable Long equipeId, @PathVariable Long entrepriseId) {
        return equipeService.assignEquipeToEntreprise(equipeId, entrepriseId);
    }

    @PutMapping("/assign-projet/{equipeId}/{projetId}")
    public Equipe assignEquipeToProjet(@PathVariable Long equipeId, @PathVariable Long projetId) {
        return equipeService.assignEquipeToProjet(equipeId, projetId);
    }

    private Equipe toEquipe(EquipeDTO equipeDTO) {
        Equipe equipe = new Equipe();
        equipe.setId(equipeDTO.getId());
        equipe.setNom(equipeDTO.getNom());
        equipe.setSpecialite(equipeDTO.getSpecialite());

        EntrepriseDTO entrepriseDTO = equipeDTO.getEntreprise();
        if (entrepriseDTO != null) {
            Entreprise entreprise = new Entreprise();
            entreprise.setId(entrepriseDTO.getId());
            entreprise.setNom(entrepriseDTO.getNom());
            entreprise.setAdresse(entrepriseDTO.getAdresse());
            equipe.setEntreprise(entreprise);
        }

        return equipe;
    }
}
