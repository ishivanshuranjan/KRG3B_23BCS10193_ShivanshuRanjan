package project.voting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import project.voting.entity.Candidate;
import project.voting.entity.Voter;
import project.voting.security.JwtTokenService;
import project.voting.service.CandidateService;
import project.voting.service.VoterService;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private VoterService voterService;

    // Get all candidates
    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token)) {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }

        return ResponseEntity.ok(candidateService.getAllCandidates());
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyAsCandidate(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Candidate candidate) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token.");
        }

        Voter voter = jwtTokenService.getVoterFromToken(token);
        if (voter == null) {
            return ResponseEntity.status(403).body("Unable to extract voter details.");
        }

        candidate.setEmail(voter.getEmail());
        return ResponseEntity.status(201).body(candidateService.applyAsCandidate(candidate));
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Candidate>> getApprovedCandidates(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token)) {
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.ok(candidateService.getAllApprovedCandidates());
    }

    @GetMapping("/results/public")
    public ResponseEntity<List<Candidate>> getPublicResults(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token)) {
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.ok(candidateService.getCandidateResults());
    }

    // Vote for a candidate
    @PostMapping("/{id}/vote")
    public ResponseEntity<?> voteForCandidate(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer id) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token."); // Unauthorized
        }

        // Extract voter information from token
        Voter voter = jwtTokenService.getVoterFromToken(token);
        if (voter == null) {
            return ResponseEntity.status(403).body("Unable to extract voter details."); // Forbidden
        }

        Voter persistedVoter = voterService.findByEmail(voter.getEmail())
            .orElse(null);
        if (persistedVoter == null) {
            return ResponseEntity.status(404).body("Voter account not found.");
        }

        try {
            Candidate updatedCandidate = candidateService.voteForCandidate(id, persistedVoter);
            return ResponseEntity.ok(updatedCandidate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Bad Request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage()); // Forbidden
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while voting."); // Internal Server Error
        }
    }
}
