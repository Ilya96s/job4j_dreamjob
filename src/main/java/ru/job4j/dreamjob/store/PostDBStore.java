package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PostDBStore работа с ДБ
 *
 * @author Ilya Kaltygin
 */
@Repository
public class PostDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class.getName());
    private final BasicDataSource pool;
    private final CityService cityService;

    public PostDBStore(BasicDataSource pool, CityService cityService) {
        this.pool = pool;
        this.cityService = cityService;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM Post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Post post = new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime());
                    post.setCity(cityService.findById(it.getInt("city_id")));
                    post.setVisible(it.getBoolean("visible"));
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method .findAll()", e);
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO Post(name, description, created, city_id, visible) VALUES(?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.setString(4, post.getCity().getName());
            ps.setBoolean(5, post.isVisible());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method .add()", e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name = ?, description = ?, created = ?, city_id = ?, visible = ? WHERE id = ?")
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.setInt(4, post.getCity().getId());
            ps.setBoolean(5, post.isVisible());
            ps.setInt(6, post.getId());
            ps.execute();
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in method .update()", e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT FROM Post WHERE id=?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    Post post = new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime());
                    post.setCity(cityService.findById(it.getInt("city_id")));
                    post.setVisible(Boolean.parseBoolean("visible"));
                    return post;
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method .findById()", e);
        }
        return null;
    }
}
