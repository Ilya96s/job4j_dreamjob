package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDbStore;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;

/**
 * CandidateService - класс,описывающий бизнес логику приложения
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
@Service
public class CandidateService {
    private final CandidateDbStore candidateStore;

    public CandidateService(CandidateDbStore candidateStore) {
        this.candidateStore = candidateStore;
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }

    public void add(Candidate candidate) {
        candidateStore.add(candidate);
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }

    public void update(Candidate candidate) {
        candidateStore.update(candidate);
    }
}
