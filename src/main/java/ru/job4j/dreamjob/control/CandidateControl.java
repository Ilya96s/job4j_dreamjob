package ru.job4j.dreamjob.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Date;

/**
 * CandidateControl - контроллер
 *
 * @author Ilya Kaltygin
 */
@Controller
public class CandidateControl {
    private final CandidateStore store = CandidateStore.instOf();

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", store.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addPost(Model model) {
        model.addAttribute("candidate", new Candidate(0, "Заполните имя", "Заполните описание", new Date()));
        return "addCandidate";
    }

}
