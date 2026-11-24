package tn.esprit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.EntrepriseDTO;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.service.IEntrepriseService;

import java.util.List;

@RestController
@RequestMapping("/entreprise")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EntrepriseController {

    IEntrepriseService entrepriseService;

    @PostMapping("/add")
    public Entreprise addEntreprise(@RequestBody EntrepriseDTO entrepriseDTO) {
        return entrepriseService.addEntreprise(toEntreprise(entrepriseDTO));
    }

    @PutMapping("/update")
    public Entreprise updateEntreprise(@RequestBody EntrepriseDTO entrepriseDTO) {
        return entrepriseService.updateEntreprise(toEntreprise(entrepriseDTO));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEntreprise(@PathVariable Long id) {
        entrepriseService.deleteEntreprise(id);
    }

    @GetMapping("/get/{id}")
    public Entreprise getEntrepriseById(@PathVariable Long id) {
        return entrepriseService.getEntrepriseById(id);
    }

    @GetMapping("/all")
    public List<Entreprise> getAllEntreprises() {
        return entrepriseService.getAllEntreprises();
    }

    private Entreprise toEntreprise(EntrepriseDTO entrepriseDTO) {
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDTO.getId());
        entreprise.setNom(entrepriseDTO.getNom());
        entreprise.setAdresse(entrepriseDTO.getAdresse());
        return entreprise;
    }
}
