package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CandidateStore - хранилище
 *
 * @author Ilya Kaltygin
 */
public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger(1);

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior Java Job", "Junior Developer, work experience 1 year", LocalDate.now()));
        candidates.put(2, new Candidate(2, "Middle Java Job", "Middle Developer, work experience 2 year", LocalDate.now()));
        candidates.put(3, new Candidate(3, "Senior Java Job", "Senior Developer, work experience 3 year", LocalDate.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        int candidateId = atomicInteger.getAndIncrement();
        candidate.setId(candidateId);
        candidates.putIfAbsent(candidateId, candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
