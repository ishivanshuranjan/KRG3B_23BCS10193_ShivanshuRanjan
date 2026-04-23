package project.voting.controller;

import project.voting.dto.VoterVerificationRequest;
import project.voting.entity.Voter;
import project.voting.service.VoterService;
import project.voting.service.VoterVerificationService;
import project.voting.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/voters")
public class VoterController {

    @Autowired
    private VoterService voterService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private VoterVerificationService voterVerificationService;

    // Register a voter
    @PostMapping("/register")
    public ResponseEntity<?> registerVoter(@RequestBody Voter voter) {
        // Check if email already exists
        Optional<Voter> existingVoter = voterService.findByEmail(voter.getEmail());
        if (existingVoter.isPresent()) {
            return ResponseEntity.status(409).body("Email is already registered."); // Conflict
        }
        
        Voter registeredVoter = voterService.registerVoter(voter);
        return ResponseEntity.status(201).body(registeredVoter); // Created
    }

    // Login (Authenticate voter and issue JWT token)
    @PostMapping("/login")
    public ResponseEntity<?> loginVoter(@RequestBody Voter voter) {
        Optional<Voter> existingVoter = voterService.findByEmail(voter.getEmail());
        
        if (existingVoter.isEmpty() || 
            !existingVoter.get().getPassword().equals(voter.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password."); // Unauthorized
        }
        
        // Generate JWT token for authenticated voter
        String token = jwtTokenService.generateToken(existingVoter.get());
        return ResponseEntity.ok(token); // Return JWT token
    }

    // Update voter details
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVoter(
            @PathVariable Integer id, 
            @RequestBody Voter voter,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Voter loggedInVoter = jwtTokenService.getVoterFromToken(token);

        if (loggedInVoter == null || loggedInVoter.getId() != id) {
            return ResponseEntity.status(403).body("Access denied."); // Forbidden
        }

        Voter updatedVoter = voterService.updateVoter(id, voter);
        return ResponseEntity.ok(updatedVoter);
    }

    // Get logged-in voter details
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInVoterDetails(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Voter loggedInVoter = jwtTokenService.getVoterFromToken(token);
        
        if (loggedInVoter == null) {
            return ResponseEntity.status(401).body("Invalid or expired token."); // Unauthorized
        }
        
        // Fetch voter details from the database to ensure freshness
        Optional<Voter> voter = voterService.findByEmail(loggedInVoter.getEmail());
        if (voter.isPresent()) {
            return ResponseEntity.ok(voter.get());
        }

        return ResponseEntity.status(404).body("Voter not found."); // Not Found
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyVoter(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody VoterVerificationRequest request) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token.");
        }

        Voter tokenVoter = jwtTokenService.getVoterFromToken(token);
        if (tokenVoter == null) {
            return ResponseEntity.status(401).body("Invalid token.");
        }

        Optional<Voter> voter = voterService.findByEmail(tokenVoter.getEmail());
        if (voter.isEmpty()) {
            return ResponseEntity.status(404).body("Voter account not found.");
        }

        try {
            Voter verifiedVoter = voterVerificationService.verifyVoter(voter.get(), request.voterId());
            return ResponseEntity.ok(verifiedVoter);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}
