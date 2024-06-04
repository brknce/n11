package com.n11.project.service;

import com.n11.project.entity.Talk;
import com.n11.project.entity.TalkScheduleEntry;
import com.n11.project.repository.TalkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final TalkRepository talkRepository;
    private final TalkScheduler talkScheduler;

    public ScheduleService(TalkRepository talkRepository, TalkScheduler talkScheduler) {
        this.talkRepository = talkRepository;
        this.talkScheduler = talkScheduler;
    }

    public List<TalkScheduleEntry> getSchedule() {
        List<Talk> talks = talkRepository.findAll();
        return talkScheduler.schedule(talks);
    }
}
