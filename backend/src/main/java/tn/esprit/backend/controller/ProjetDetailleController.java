package tn.esprit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.ProjetDetailleDTO;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.service.IProjetDetailleService;

import java.util.List;

@RestController
@RequestMapping("/projet-detaille")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetDetailleController {

    IProjetDetailleService projetDetailleService;

    @PostMapping("/add")
    public ProjetDetaille addProjetDetaille(@RequestBody ProjetDetailleDTO projetDetailleDTO) {
        return projetDetailleService.addProjetDetaille(toProjetDetaille(projetDetailleDTO));
    }

    @PutMapping("/update")
    public ProjetDetaille updateProjetDetaille(@RequestBody ProjetDetailleDTO projetDetailleDTO) {
        return projetDetailleService.updateProjetDetaille(toProjetDetaille(projetDetailleDTO));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProjetDetaille(@PathVariable Long id) {
        projetDetailleService.deleteProjetDetaille(id);
    }

    @GetMapping("/get/{id}")
    public ProjetDetaille getProjetDetailleById(@PathVariable Long id) {
        return projetDetailleService.getProjetDetailleById(id);
    }

    @GetMapping("/all")
    public List<ProjetDetaille> getAllProjetsDetailles() {
        return projetDetailleService.getAllProjetsDetailles();
    }

    @GetMapping("/by-projet/{projetId}")
    public List<ProjetDetaille> getProjetDetaillesByProjet(@PathVariable Long projetId) {
        return projetDetailleService.getProjetDetaillesByProjet(projetId);
    }

    @PutMapping("/assign-projet/{projetDetailleId}/{projetId}")
    public ProjetDetaille assignProjetDetailleToProjet(@PathVariable Long projetDetailleId, @PathVariable Long projetId) {
        return projetDetailleService.assignProjetDetailleToProjet(projetDetailleId, projetId);
    }

    private ProjetDetaille toProjetDetaille(ProjetDetailleDTO projetDetailleDTO) {
        ProjetDetaille projetDetaille = new ProjetDetaille();
        projetDetaille.setId(projetDetailleDTO.getId());
        projetDetaille.setDescription(projetDetailleDTO.getDescription());
        projetDetaille.setTechnologie(projetDetailleDTO.getTechnologie());
        projetDetaille.setCoutProvisoire(projetDetailleDTO.getCoutProvisoire());
        projetDetaille.setDateDebut(projetDetailleDTO.getDateDebut());
        return projetDetaille;
    }
}
