package ru.job4j.dreamjob.control;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * PostController - контроллер
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
@Controller
public class PostControl {
    @GuardedBy("this")
    private PostService postService;
    private final CityService cityService;

    public PostControl(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("user", user);
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("post", new Post(0, "Заполните имя", "Заполните описание", LocalDateTime.now()));
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("user", user);
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        post.setCreated(LocalDateTime.now());
        post.setCity(cityService.findById(post.getCity().getId()));
        postService.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("user", user);
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        post.setCreated(LocalDateTime.now());
        post.setCity(cityService.findById(post.getCity().getId()));
        postService.update(post);
        return "redirect:/posts";
    }
}
