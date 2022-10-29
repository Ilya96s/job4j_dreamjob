package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostDBStoreTest {

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "Description", LocalDateTime.now());
        post.setCity(new City(0, "City"));
        post.setVisible(true);
        store.add(post);
        Post postInDB = store.findById(post.getId());
        assertThat(postInDB.getName()).isEqualTo(post.getName());
        assertThat(postInDB.getDescription()).isEqualTo(post.getDescription());
        assertThat(postInDB.getCity()).isEqualTo(post.getCity());
        assertThat(postInDB.isVisible()).isEqualTo(post.isVisible());
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "Description", LocalDateTime.now());
        post.setCity(new City(0, "City"));
        post.setVisible(true);
        store.add(post);

        Post updatedPost = new Post(post.getId(), "Updated Java Job", "Updated Description", LocalDateTime.now());
        updatedPost.setCity(new City(0, "Updated City"));
        updatedPost.setVisible(true);
        store.update(updatedPost);
        Post postInDB = store.findById(post.getId());
        assertThat(postInDB.getName()).isEqualTo(updatedPost.getName());
        assertThat(postInDB.getDescription()).isEqualTo(updatedPost.getDescription());
        assertThat(postInDB.getCity()).isEqualTo(updatedPost.getCity());
        assertThat(postInDB.isVisible()).isEqualTo(updatedPost.isVisible());
    }

    @Test
    public void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post1 = new Post(0, "Java Job 1", "Description 1", LocalDateTime.now());
        post1.setCity(new City(0, "City 1"));
        post1.setVisible(true);
        store.add(post1);
        Post post2 = new Post(1, "Java Job 2", "Description 2", LocalDateTime.now());
        post2.setCity(new City(1, "City 2"));
        post2.setVisible(false);
        store.add(post2);
        assertThat(store.findAll()).contains(post1, post2);
    }

}