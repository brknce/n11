package com.n11.project.repository;

import com.n11.project.entity.Talk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TalkRepository extends JpaRepository<Talk, Long> {
    Optional<Talk> findByTitleAndDuration(String title, int duration);

}
