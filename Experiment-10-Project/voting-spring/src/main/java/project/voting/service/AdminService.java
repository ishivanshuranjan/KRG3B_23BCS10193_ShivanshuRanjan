package project.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import project.voting.entity.Candidate;
import project.voting.entity.Voter;
import project.voting.repository.CandidateRepository;
import project.voting.repository.VoterRepository;

@Service
public class AdminService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    // Voter Management
    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public Voter updateVoter(Integer voterId, Voter updatedVoter) {
        Voter voter = voterRepository.findById(voterId).orElseThrow(() -> 
            new IllegalArgumentException("Voter not found")
        );
        voter.setName(updatedVoter.getName());
        voter.setEmail(updatedVoter.getEmail());
        if (updatedVoter.getPassword() != null && !updatedVoter.getPassword().isBlank()) {
            voter.setPassword(updatedVoter.getPassword());
        }
        voter.setIsAdmin(updatedVoter.getIsAdmin());
        return voterRepository.save(voter);
    }

    public void deleteVoter(Integer voterId) {
        voterRepository.deleteById(voterId);
    }

    public Voter promoteAdminByEmail(String email) {
        Voter voter = voterRepository.findByEmail(email).orElseThrow(() ->
            new IllegalArgumentException("Voter not found")
        );
        voter.setIsAdmin(true);
        return voterRepository.save(voter);
    }

    // Candidate Management
    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(Integer candidateId, Candidate updatedCandidate) {
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> 
            new IllegalArgumentException("Candidate not found")
        );
        candidate.setName(updatedCandidate.getName());
        candidate.setParty(updatedCandidate.getParty());
        return candidateRepository.save(candidate);
    }

    public void deleteCandidate(Integer candidateId) {
        candidateRepository.deleteById(candidateId);
    }

    public List<Candidate> getPendingCandidates() {
        return candidateRepository.findByStatus("PENDING");
    }

    public Candidate approveCandidate(Integer id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Candidate not found")
        );
        candidate.setStatus("APPROVED");
        return candidateRepository.save(candidate);
    }

    public Candidate rejectCandidate(Integer id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Candidate not found")
        );
        candidate.setStatus("REJECTED");
        return candidateRepository.save(candidate);
    }

    // View Results
    public List<Candidate> getResults() {
        return candidateRepository.findAll(); // Vote counts are already stored in Candidate entity
    }
}
