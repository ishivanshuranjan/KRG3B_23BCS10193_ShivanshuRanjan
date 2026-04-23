package project.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.voting.entity.EligibleVoter;

import java.util.Optional;

public interface EligibleVoterRepository extends JpaRepository<EligibleVoter, Integer> {

    Optional<EligibleVoter> findByVoterIdIgnoreCase(String voterId);
}
