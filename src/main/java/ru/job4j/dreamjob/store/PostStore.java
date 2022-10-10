package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * PostStore - хранилище
 *
 * @author Ilya Kaltygin
 */
public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger(1);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Junior Developer", new Date()));
        posts.put(2, new Post(2, "Middle Java Job", "Middle Developer", new Date()));
        posts.put(3, new Post(3, "Senior Java Job", "Senior Developer", new Date()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        int postId = atomicInteger.getAndIncrement();
        post.setId(postId);
        posts.putIfAbsent(postId, post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
