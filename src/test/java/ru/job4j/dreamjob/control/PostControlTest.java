package ru.job4j.dreamjob.control;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для контроллера PostControl с использованием библиотеки Mockito
 *
 * @author Ilya Kaltygin
 */
class PostControlTest {

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "Java Dev 1", "Java Junior Developer 1", LocalDateTime.now()),
                new Post(2, "Java Dev 2", "Java Junior Developer 2", LocalDateTime.now())
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(postService.findAll()).thenReturn(posts);
        PostControl postControl = new PostControl(postService, cityService);
        String page = postControl.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "Java Dev 1", "Java Junior Developer", LocalDateTime.now());
        input.setCity(new City(1, "Москва"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(postService, cityService);
        String page = postControl.createPost(input);
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void addPost() {
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "ЕКБ"),
                new City(3, "СПБ")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        CityService cityService = mock(CityService.class);
        PostService postService = mock(PostService.class);
        PostControl postControl = new PostControl(postService, cityService);
        when(cityService.getAllCities()).thenReturn(cities);
        String page = postControl.addPost(model, session);
        verify(model).addAttribute("cities", cityService.getAllCities());
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1, "Java Dev 1", "Java Junior Developer 1", LocalDateTime.now());
        input.setCity(new City(1, "Москва"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(postService, cityService);
        String page = postControl.updatePost(input);
        verify(postService).update(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

}