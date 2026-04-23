package project.voting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import project.voting.dto.BootstrapPromoteRequest;
import project.voting.entity.Candidate;
import project.voting.entity.Voter;
import project.voting.security.JwtTokenService;
import project.voting.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtTokenService jwtTokenService;

    // Utility method to validate admin role
    private boolean isAdmin(String token) {
        Voter voter = jwtTokenService.getVoterFromToken(token);
        return voter != null && voter.getIsAdmin(); // Ensure isAdmin() exists in the Voter entity
    }

    // Voter Management
    @GetMapping("/voters")
    public ResponseEntity<List<Voter>> getAllVoters(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).body(null); // 403 Forbidden for non-admin users
        }
        return ResponseEntity.ok(adminService.getAllVoters());
    }

    @PutMapping("/voters/{id}")
    public ResponseEntity<Voter> updateVoter(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer id,
            @RequestBody Voter updatedVoter) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).build(); // 403 Forbidden for non-admin users
        }
        return ResponseEntity.ok(adminService.updateVoter(id, updatedVoter));
    }

    @DeleteMapping("/voters/{id}")
    public ResponseEntity<String> deleteVoter(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer id) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).build(); // 403 Forbidden for non-admin users
        }
        adminService.deleteVoter(id);
        return ResponseEntity.ok("Voter deleted successfully");
    }

    // Candidate Management
    @PostMapping("/candidates")
    public ResponseEntity<Candidate> addCandidate(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Candidate candidate) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).build(); // 403 Forbidden for non-admin users
        }
        return ResponseEntity.ok(adminService.addCandidate(candidate));
    }

    @PutMapping("/candidates/{id}")
    public ResponseEntity<Candidate> updateCandidate(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer id,
            @RequestBody Candidate updatedCandidate) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).build(); // 403 Forbidden for non-admin users
        }
        return ResponseEntity.ok(adminService.updateCandidate(id, updatedCandidate));
    }

    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<String> deleteCandidate(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer id) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).build(); // 403 Forbidden for non-admin users
        }
        adminService.deleteCandidate(id);
        return ResponseEntity.ok("Candidate deleted successfully");
    }

    @GetMapping("/candidates/pending")
    public ResponseEntity<List<Candidate>> getPendingCandidates(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(adminService.getPendingCandidates());
    }

    @PutMapping("/candidates/{id}/approve")
    public ResponseEntity<Candidate> approveCandidate(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer id) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(adminService.approveCandidate(id));
    }

    @PutMapping("/candidates/{id}/reject")
    public ResponseEntity<Candidate> rejectCandidate(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer id) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(adminService.rejectCandidate(id));
    }

    // View Results
    @GetMapping("/results")
    public ResponseEntity<List<Candidate>> getResults(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenService.validateToken(token) || !isAdmin(token)) {
            return ResponseEntity.status(403).body(null); // 403 Forbidden for non-admin users
        }
        return ResponseEntity.ok(adminService.getResults());
    }

    // Bootstrap admin promotion for initial setup only.
    @PostMapping("/bootstrap/promote")
    public ResponseEntity<?> bootstrapPromote(
            @RequestHeader("X-Bootstrap-Key") String bootstrapKey,
            @RequestBody BootstrapPromoteRequest request) {
        String expectedKey = System.getenv("ADMIN_BOOTSTRAP_KEY");
        if (expectedKey == null || expectedKey.isBlank()) {
            return ResponseEntity.status(503).body("Bootstrap key not configured.");
        }
        if (!expectedKey.equals(bootstrapKey)) {
            return ResponseEntity.status(403).body("Invalid bootstrap key.");
        }
        if (request == null || request.email() == null || request.email().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }
        try {
            Voter updated = adminService.promoteAdminByEmail(request.email().trim());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
