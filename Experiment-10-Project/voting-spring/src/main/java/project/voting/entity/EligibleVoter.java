package project.voting.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eligible_voters")
public class EligibleVoter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String voterId;

    @Column(nullable = false)
    private boolean claimed = false;

    private String claimedByEmail;

    private LocalDateTime claimedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId == null ? null : voterId.trim().toUpperCase();
    }

    public boolean getClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public String getClaimedByEmail() {
        return claimedByEmail;
    }

    public void setClaimedByEmail(String claimedByEmail) {
        this.claimedByEmail = claimedByEmail;
    }

    public LocalDateTime getClaimedAt() {
        return claimedAt;
    }

    public void setClaimedAt(LocalDateTime claimedAt) {
        this.claimedAt = claimedAt;
    }
}
