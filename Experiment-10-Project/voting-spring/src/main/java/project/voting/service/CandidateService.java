package project.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import project.voting.entity.Candidate;
import project.voting.entity.Voter;
import project.voting.repository.CandidateRepository;
import project.voting.repository.VoterRepository;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoterRepository voterRepository;

    // Get all candidates
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate applyAsCandidate(Candidate candidate) {
        candidate.setStatus("PENDING");
        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllApprovedCandidates() {
        return candidateRepository.findByStatus("APPROVED");
    }

    public List<Candidate> getCandidateResults() {
        return candidateRepository.findByStatus("APPROVED")
            .stream()
            .sorted((a, b) -> b.getVoteCount() - a.getVoteCount())
            .collect(java.util.stream.Collectors.toList());
    }

    // Increment vote count
    @Transactional
    public Candidate voteForCandidate(Integer candidateId, Voter voter) {
        if (!voter.getIsVerified()) {
            throw new IllegalStateException("Verify your voter ID before casting a ballot.");
        }

        if (voter.getHasVoted()) {
            throw new IllegalStateException("Your vote has already been recorded.");
        }

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> 
            new IllegalArgumentException("Candidate not found")
        );
        candidate.setVoteCount(candidate.getVoteCount() + 1);
        voter.setHasVoted(true);
        voterRepository.save(voter);
        return candidateRepository.save(candidate);
    }
}
