package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class CandidateDbStore {
    private static final Logger LOG = LoggerFactory.getLogger(CandidateDbStore.class.getName());
    private static final String FIND_ALL = "SELECT * FROM Candidate";
    private static final String ADD_CANDIDATE = "INSERT INTO Candidate(name, visible, city_id, desc, created) VALUES(?, ?, ?, ?, ?)";
    private static final String FIND_CANDIDATE_BY_ID = "SELECT * FROM Candidate WHERE id=?";
    private static final String UPDATE_CANDIDATE = "UPDATE Candidate SET name = ?, visible = ?, city_id = ?, desc = ?, created = ? WHERE id = ?";
    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(createCandidate(it));
                }
            }

        } catch (Exception e) {
            LOG.error("Exception in method .findAll()", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD_CANDIDATE, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setBoolean(2, candidate.isVisible());
            ps.setString(3, candidate.getCity().getName());
            ps.setString(4, candidate.getDesc());
            ps.setTimestamp(5, Timestamp.valueOf(candidate.getCreated()));
            ps.setInt(6, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in method .add()", e);
        }
        return candidate;
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(FIND_CANDIDATE_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createCandidate(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method .findById()", e);
        }
        return null;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(UPDATE_CANDIDATE)
        ) {
            ps.setString(1, candidate.getName());
            ps.setBoolean(2, candidate.isVisible());
            ps.setString(3, candidate.getCity().getName());
            ps.setString(4, candidate.getDesc());
            ps.setTimestamp(5, Timestamp.valueOf(candidate.getCreated()));
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in method .update()", e);
        }
    }

    private Candidate createCandidate(ResultSet it) throws SQLException {
        Candidate candidate = new Candidate();
        candidate.setId(it.getInt("id"));
        candidate.setName(it.getString("name"));
        candidate.setVisible(it.getBoolean("visible"));
        candidate.setDesc(it.getString("desc"));
        candidate.setCreated(it.getTimestamp("created").toLocalDateTime());
        candidate.setCity(new City(it.getInt("city_id"), ""));
        return candidate;
    }
}
