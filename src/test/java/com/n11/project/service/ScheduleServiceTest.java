package com.n11.project.service;

import com.n11.project.entity.Talk;
import com.n11.project.entity.TalkScheduleEntry;
import com.n11.project.repository.TalkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private TalkRepository talkRepository;

    @Mock
    private TalkScheduler talkScheduler;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void it_should_return_scheduled_talks() {
        List<Talk> talks = Arrays.asList(
                new Talk(1L, "Talk 1", 30),
                new Talk(2L, "Talk 2", 45)
        );

        List<TalkScheduleEntry> scheduledEntries = Arrays.asList(
                new TalkScheduleEntry(),
                new TalkScheduleEntry()
        );

        when(talkRepository.findAll()).thenReturn(talks);

        when(talkScheduler.schedule(talks)).thenReturn(scheduledEntries);

        List<TalkScheduleEntry> result = scheduleService.getSchedule();

        assertEquals(scheduledEntries, result);

        verify(talkRepository, times(1)).findAll();
        verify(talkScheduler, times(1)).schedule(talks);
    }
}