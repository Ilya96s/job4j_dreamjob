package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CandidateStore - хранилище
 *
 * @author Ilya Kaltygin
 */
public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior Java Job", "Junior Developer, work experience 1 year", new Date()));
        candidates.put(2, new Candidate(2, "Middle Java Job", "Middle Developer, work experience 2 year", new Date()));
        candidates.put(3, new Candidate(3, "Senior Java Job", "Senior Developer, work experience 3 year", new Date()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
