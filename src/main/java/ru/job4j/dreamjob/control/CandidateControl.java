package ru.job4j.dreamjob.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import java.time.LocalDate;

/**
 * CandidateControl - контроллер
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
@Controller
public class CandidateControl {
    private final CandidateService candidateService;
    private CityService cityService;

    public CandidateControl(CandidateService candidateService, CityService cityService) {
        this.candidateService = candidateService;
        this.cityService = cityService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate", new Candidate(0, "Заполните имя", "Заполните описание", LocalDate.now()));
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createPost(@ModelAttribute Candidate candidate) {
        candidate.setCreated(LocalDate.now());
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdatePost(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", candidateService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updatePost(@ModelAttribute Candidate candidate) {
        candidate.setCreated(LocalDate.now());
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        candidateService.update(candidate);
        return "redirect:/candidates";
    }

}
