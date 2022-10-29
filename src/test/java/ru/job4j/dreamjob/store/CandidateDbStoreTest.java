package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class CandidateDbStoreTest {

    @Test
    public void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Java Dev", "Description", LocalDateTime.now());
        candidate.setVisible(true);
        candidate.setCity(new City(0, "City"));
        store.add(candidate);
        Candidate candidateInDB = store.findById(candidate.getId());
        assertThat(candidateInDB.getName()).isEqualTo(candidate.getName());
        assertThat(candidateInDB.getDesc()).isEqualTo(candidate.getDesc());
        assertThat(candidateInDB.isVisible()).isEqualTo(candidate.isVisible());
        assertThat(candidateInDB.getCity()).isEqualTo(candidate.getCity());
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(0, "Java Dev", "Description", LocalDateTime.now());
        candidate1.setCity(new City(0, "City"));
        candidate1.setVisible(true);
        store.add(candidate1);
        Candidate candidate2 = new Candidate(candidate1.getId(), "Updated Java Dev", "Updated Description", LocalDateTime.now());
        candidate2.setCity(new City(0, "Updated City"));
        candidate2.setVisible(true);
        store.update(candidate2);
        Candidate candidateInDB = store.findById(candidate1.getId());
        assertThat(candidateInDB.getName()).isEqualTo(candidate2.getName());
        assertThat(candidateInDB.getDesc()).isEqualTo(candidate2.getDesc());
        assertThat(candidateInDB.isVisible()).isEqualTo(candidate2.isVisible());
        assertThat(candidateInDB.getCity()).isEqualTo(candidate2.getCity());
    }

    @Test
    public void whenFindAllCandidates() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(0, "Java Dev 1", "Description 1", LocalDateTime.now());
        candidate1.setCity(new City(0, "City 1"));
        candidate1.setVisible(true);
        store.add(candidate1);
        Candidate candidate2 = new Candidate(1, "Java Dev 2", "Description 2", LocalDateTime.now());
        candidate2.setCity(new City(0, "City 2"));
        candidate2.setVisible(true);
        store.add(candidate2);
        assertThat(store.findAll()).contains(candidate1, candidate2);
    }

}