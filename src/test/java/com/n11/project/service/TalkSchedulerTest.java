package com.n11.project.service;

import com.n11.project.entity.Talk;
import com.n11.project.entity.TalkScheduleEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class TalkSchedulerTest {
    @InjectMocks
    private TalkScheduler scheduler;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    public void it_should_test_basic_scheduling() {
        List<Talk> talks = List.of(
                new Talk(null, "Talk 1", 60),
                new Talk(null, "Talk 2", 45),
                new Talk(null, "Talk 3", 30)
        );

        List<TalkScheduleEntry> schedule = scheduler.schedule(talks);

        assertEquals(1, schedule.size());
        TalkScheduleEntry track = schedule.get(0);
        assertTrue(track.getScheduledEntry().containsKey("09:00AM"));
        assertTrue(track.getScheduledEntry().containsKey("10:00AM"));
        assertTrue(track.getScheduledEntry().containsKey("10:45AM"));
    }

    @Test
    public void it_should_test_multiple_tracks() {
        List<Talk> talks = List.of(
                new Talk(null, "Talk 1", 60),
                new Talk(null, "Talk 2", 45),
                new Talk(null, "Talk 3", 30),
                new Talk(null, "Talk 4", 60),
                new Talk(null, "Talk 5", 60),
                new Talk(null, "Talk 6", 45),
                new Talk(null, "Talk 5", 60),
                new Talk(null, "Talk 6", 45),
                new Talk(null, "Talk 5", 60),
                new Talk(null, "Talk 6", 45)

        );

        List<TalkScheduleEntry> schedule = scheduler.schedule(talks);

        assertEquals(2, schedule.size());
    }


    @Test
    public void it_should_test_empty_talk_list() {
        List<Talk> talks = new ArrayList<>();

        TalkScheduler scheduler = new TalkScheduler();
        List<TalkScheduleEntry> schedule = scheduler.schedule(talks);

        assertEquals(0, schedule.size());
    }

    @Test
    public void it_should_test_talk_exceeding_time_limits() {
        List<Talk> talks = List.of(
                new Talk(null, "Talk 1", 240),
                new Talk(null, "Talk 2", 120)
        );

        List<TalkScheduleEntry> schedule = scheduler.schedule(talks);

        assertEquals(1, schedule.size());
        TalkScheduleEntry track = schedule.get(0);
        assertTrue(track.getScheduledEntry().containsKey("01:00PM"));
    }

    @Test
    public void it_should_test_mixed_duration_talks() {
        List<Talk> talks = List.of(
                new Talk(null, "Talk 1", 60),
                new Talk(null, "Talk 2", 15),
                new Talk(null, "Talk 3", 45),
                new Talk(null, "Talk 4", 30),
                new Talk(null, "Talk 5", 5)
        );

        List<TalkScheduleEntry> schedule = scheduler.schedule(talks);

        assertEquals(1, schedule.size());
        TalkScheduleEntry track = schedule.get(0);
        assertTrue(track.getScheduledEntry().containsKey("09:00AM"));
        assertTrue(track.getScheduledEntry().containsKey("10:00AM"));
        assertTrue(track.getScheduledEntry().containsKey("10:15AM"));
        assertTrue(track.getScheduledEntry().containsKey("11:30AM"));
    }


    @Test
    public void it_should_test_exact_fit() {
        List<Talk> talks = List.of(
                new Talk(null, "Talk 1", 60),
                new Talk(null, "Talk 2", 60),
                new Talk(null, "Talk 3", 60)
        );

        List<TalkScheduleEntry> schedule = scheduler.schedule(talks);

        assertEquals(1, schedule.size());
        TalkScheduleEntry track = schedule.get(0);
        assertTrue(track.getScheduledEntry().containsKey("09:00AM"));
        assertTrue(track.getScheduledEntry().containsKey("10:00AM"));
        assertTrue(track.getScheduledEntry().containsKey("11:00AM"));
        assertTrue(track.getScheduledEntry().containsKey("12:00PM"));
    }

    @Test
    public void it_should_test_overflow_to_new_track() {
        List<Talk> talks = List.of(
                new Talk(null, "Talk 1", 60),
                new Talk(null, "Talk 2", 60),
                new Talk(null, "Talk 3", 60),
                new Talk(null, "Talk 4", 60),
                new Talk(null, "Talk 5", 60),
                new Talk(null, "Talk 6", 60),
                new Talk(null, "Talk 5", 60),
                new Talk(null, "Talk 6", 60)
        );

        List<TalkScheduleEntry> schedule = scheduler.schedule(talks);

        assertEquals(2, schedule.size());
        TalkScheduleEntry track1 = schedule.get(0);
        TalkScheduleEntry track2 = schedule.get(1);

        assertEquals("Track 1", track1.getTrackName());
        assertEquals("Track 2", track2.getTrackName());
    }

}