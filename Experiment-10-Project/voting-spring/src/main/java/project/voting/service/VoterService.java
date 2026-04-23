package project.voting.service;

import project.voting.entity.Voter;
import project.voting.repository.VoterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    // Register a new voter
    public Voter registerVoter(Voter voter) {
        voter.setVoterId(null);
        voter.setIsVerified(false);
        voter.setHasVoted(false);
        return voterRepository.save(voter);
    }

    // Find a voter by email (for login)
    public Optional<Voter> findByEmail(String email) {
        return voterRepository.findByEmail(email);
    }

    // Update voter details
    public Voter updateVoter(Integer voterId, Voter updatedVoter) {
        Voter voter = voterRepository.findById(voterId).orElseThrow(() ->
            new IllegalArgumentException("Voter not found")
        );
        voter.setName(updatedVoter.getName());
        voter.setEmail(updatedVoter.getEmail());

        if (updatedVoter.getPassword() != null && !updatedVoter.getPassword().isBlank()) {
            voter.setPassword(updatedVoter.getPassword());
        }

        return voterRepository.save(voter);
    }

    //getLoggedInVoter
    public Voter getLoggedInVoter() {
        return null;
    }
}
