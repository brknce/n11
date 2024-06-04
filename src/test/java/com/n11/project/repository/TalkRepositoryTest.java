package com.n11.project.repository;

import com.n11.project.entity.Talk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TalkRepositoryTest {
    @Autowired
    private TalkRepository talkRepository;

    private Talk talk1;
    private Talk talk2;

    @BeforeEach
    void setUp() {
        talk1 = new Talk(null, "Test Talk 1", 30);
        talk2 = new Talk(null, "Test Talk 2", 45);
        talkRepository.save(talk1);
        talkRepository.save(talk2);
    }

    @Test
    void it_should_talk_find_by_title_and_duration() {
        Optional<Talk> foundTalk = talkRepository.findByTitleAndDuration("Test Talk 1", 30);
        assertThat(foundTalk).isPresent();
        assertThat(foundTalk.get().getTitle()).isEqualTo(talk1.getTitle());
        assertThat(foundTalk.get().getDuration()).isEqualTo(talk1.getDuration());
    }

    @Test
    public void it_should_find_by_title_and_duration_not_found() {
        Optional<Talk> foundTalk = talkRepository.findByTitleAndDuration("Non Existent Talk", 60);
        assertThat(foundTalk).isNotPresent();
    }
}