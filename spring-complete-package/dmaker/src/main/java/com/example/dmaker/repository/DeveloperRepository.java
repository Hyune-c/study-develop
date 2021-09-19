package com.example.dmaker.repository;

import com.example.dmaker.entity.Developer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

	Optional<Developer> findByMemberId(String memberId);
}
