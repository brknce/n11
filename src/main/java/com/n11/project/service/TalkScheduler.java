package com.n11.project.service;

import com.n11.project.entity.Talk;
import com.n11.project.entity.TalkScheduleEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TalkScheduler {
    private static final int MORNING_SESSION_START = 9 * 60;
    private static final int MORNING_SESSION_END = 12 * 60;
    private static final int AFTERNOON_SESSION_START = 13 * 60;
    private static final int AFTERNOON_SESSION_END = 17 * 60;
    private static final int NETWORKING_EVENT_MIN_START = 16 * 60;

    public List<TalkScheduleEntry> schedule(List<Talk> talks) {
        List<TalkScheduleEntry> tracks = new ArrayList<>();
        int trackCounter = 1;

        while (!talks.isEmpty()) {
            TalkScheduleEntry track = new TalkScheduleEntry();
            track.setTrackName("Track " + trackCounter++);
            log.info("Creating {}", track.getTrackName());
            Map<String, String> schedule = track.getScheduledEntry();
            final int[] morningTime = {MORNING_SESSION_START};
            final int[] afternoonTime = {AFTERNOON_SESSION_START};

            List<Talk> remainingTalks = new ArrayList<>(talks);

            List<Talk> morningTalks = remainingTalks.stream()
                    .filter(talk -> {
                        if (morningTime[0] + talk.getDuration() <= MORNING_SESSION_END) {
                            schedule.put(formatTime(morningTime[0]), talk.getTitle() + " " + talk.getDuration() + "min");
                            morningTime[0] += talk.getDuration();
                            return false;
                        }
                        return true;
                    })
                    .toList();

            schedule.put("12:00PM", "Lunch");

            remainingTalks = morningTalks.stream()
                    .filter(talk -> {
                        if (afternoonTime[0] + talk.getDuration() <= AFTERNOON_SESSION_END) {
                            schedule.put(formatTime(afternoonTime[0]), talk.getTitle() + " " + talk.getDuration() + "min");
                            afternoonTime[0] += talk.getDuration();
                            return false;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());

            if (afternoonTime[0] <= NETWORKING_EVENT_MIN_START) {
                schedule.put("04:00PM", "Networking Event");
            } else if (afternoonTime[0] < AFTERNOON_SESSION_END) {
                schedule.put(formatTime(afternoonTime[0]), "Networking Event");
            }

            log.info("Scheduled {}", track.getTrackName());

            tracks.add(track);
            talks = remainingTalks;
        }

        return tracks;
    }

    private String formatTime(int minutes) {
        int midOfDay = 12;
        int minutesInHour = 60;
        int midNight = 0;
        int hours = (minutes / minutesInHour) % midOfDay;
        hours = hours == midNight ? midOfDay : hours;
        int mins = minutes % minutesInHour;
        String period = (minutes / minutesInHour) < midOfDay ? "AM" : "PM";
        return String.format("%02d:%02d%s", hours, mins, period);
    }
}
