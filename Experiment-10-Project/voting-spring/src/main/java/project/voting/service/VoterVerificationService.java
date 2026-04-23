package project.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.voting.entity.EligibleVoter;
import project.voting.entity.Voter;
import project.voting.repository.EligibleVoterRepository;
import project.voting.repository.VoterRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoterVerificationService {

    @Autowired
    private EligibleVoterRepository eligibleVoterRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Transactional
    public Voter verifyVoter(Voter voter, String rawVoterId) {
        String normalizedVoterId = normalizeVoterId(rawVoterId);
        if (normalizedVoterId.isBlank()) {
            throw new IllegalArgumentException("Voter ID is required.");
        }

        EligibleVoter eligibleVoter = eligibleVoterRepository.findByVoterIdIgnoreCase(normalizedVoterId)
            .orElseThrow(() -> new IllegalArgumentException("This voter ID is not eligible to vote."));

        Optional<Voter> voterWithSameId = voterRepository.findByVoterIdIgnoreCase(normalizedVoterId);
        if (voterWithSameId.isPresent() && voterWithSameId.get().getId() != voter.getId()) {
            throw new IllegalStateException("This voter ID is already linked to another account.");
        }

        if (voter.getIsVerified() && voter.getVoterId() != null
                && !voter.getVoterId().equalsIgnoreCase(normalizedVoterId)) {
            throw new IllegalStateException("This account is already verified with a different voter ID.");
        }

        if (eligibleVoter.getClaimed()) {
            String claimedByEmail = eligibleVoter.getClaimedByEmail();
            if (claimedByEmail == null || !claimedByEmail.equalsIgnoreCase(voter.getEmail())) {
                throw new IllegalStateException("This voter ID has already been used.");
            }
        }

        voter.setVoterId(normalizedVoterId);
        voter.setIsVerified(true);
        Voter savedVoter = voterRepository.save(voter);

        eligibleVoter.setClaimed(true);
        eligibleVoter.setClaimedByEmail(savedVoter.getEmail());
        eligibleVoter.setClaimedAt(LocalDateTime.now());
        eligibleVoterRepository.save(eligibleVoter);

        return savedVoter;
    }

    private String normalizeVoterId(String voterId) {
        return voterId == null ? "" : voterId.trim().toUpperCase();
    }
}
