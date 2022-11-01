package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.utility.HttpSessionUtility;

import javax.servlet.http.HttpSession;

@Controller
public class IndexControl {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        return "index";
    }
}