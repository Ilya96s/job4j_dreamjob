package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;

import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PostDBStoreTest {

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "Middle Java Dev", LocalDateTime.now());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

}