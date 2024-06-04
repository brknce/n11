package com.n11.project.service;

import com.n11.project.dto.TalkAddRequest;
import com.n11.project.dto.TalkDeleteRequest;
import com.n11.project.dto.TalkUpdateRequest;
import com.n11.project.entity.Talk;
import com.n11.project.exception.DuplicateTalkException;
import com.n11.project.exception.TalkNotFoundException;
import com.n11.project.repository.TalkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TalkService {
    private final TalkRepository talkRepository;

    public TalkService(TalkRepository talkRepository) {
        this.talkRepository = talkRepository;
    }

    public void addTalks(TalkAddRequest talkAddRequests) {
        int lightningTime = 5;
        List<Talk> talks = talkAddRequests.getTalks().stream()
                .map(talkItem -> Talk.builder()
                        .duration(talkItem.getTitle().toLowerCase().contains("lightning") ? lightningTime : talkItem.getDuration())
                        .title(talkItem.getTitle())
                        .build())
                .collect(Collectors.toList());

        talks.stream()
                .filter(talk -> talkRepository.findByTitleAndDuration(talk.getTitle(), talk.getDuration()).isPresent())
                .findFirst()
                .ifPresent(talk -> {
                    log.error("Duplicate talk detected: {} with duration {}min", talk.getTitle(), talk.getDuration());
                    throw new DuplicateTalkException("Talk with title '" + talk.getTitle() + "' and duration '" + talk.getDuration() + "' already exists");
                });

        talkRepository.saveAll(talks);
        log.info("Added {} talks successfully", talks.size());
    }

    public void updateTalk(TalkUpdateRequest updateTalkRequest) {
        Talk existingTalk = talkRepository.findByTitleAndDuration(updateTalkRequest.getTitle(), updateTalkRequest.getDuration())
                .orElseThrow(() -> {
                    log.error("Talk not found: {} with duration {}min", updateTalkRequest.getTitle(), updateTalkRequest.getDuration());
                    return new TalkNotFoundException("Talk with title '" + updateTalkRequest.getTitle() + "' and duration '" + updateTalkRequest.getDuration() + "' not found");
                });
        existingTalk.setTitle(updateTalkRequest.getNewTitle());
        existingTalk.setDuration(updateTalkRequest.getNewDuration());

        talkRepository.save(existingTalk);
        log.info("Updated talk: {} with new title {} and new duration {}min", updateTalkRequest.getTitle(), updateTalkRequest.getNewTitle(), updateTalkRequest.getNewDuration());
    }

    public void deleteTalk(TalkDeleteRequest deleteTalkRequest) {
        Talk existingTalk = talkRepository.findByTitleAndDuration(deleteTalkRequest.getTitle(), deleteTalkRequest.getDuration())
                .orElseThrow(() -> {
                    log.error("Talk not found: {} with duration {}min", deleteTalkRequest.getTitle(), deleteTalkRequest.getDuration());
                    return new TalkNotFoundException("Talk with title '" + deleteTalkRequest.getTitle() + "' and duration '" + deleteTalkRequest.getDuration() + "' not found");
                });

        talkRepository.delete(existingTalk);
        log.info("Deleted talk: {} with duration {}min", deleteTalkRequest.getTitle(), deleteTalkRequest.getDuration());

    }
}
