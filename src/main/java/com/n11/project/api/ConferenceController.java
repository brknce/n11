package com.n11.project.api;

import com.n11.project.dto.TalkAddRequest;
import com.n11.project.dto.TalkDeleteRequest;
import com.n11.project.dto.TalkUpdateRequest;
import com.n11.project.entity.TalkScheduleEntry;
import com.n11.project.service.ScheduleService;
import com.n11.project.service.TalkService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talks")
public class ConferenceController {
    private final ScheduleService scheduleService;
    private final TalkService talkService;


    public ConferenceController(ScheduleService scheduleService, TalkService talkService) {
        this.scheduleService = scheduleService;
        this.talkService = talkService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTalks(@RequestBody @Valid TalkAddRequest talkAddRequests) {
        talkService.addTalks(talkAddRequests);
        return ResponseEntity.ok("Talks added successfully");
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<TalkScheduleEntry>> scheduleTalks() {
        List<TalkScheduleEntry> schedule = scheduleService.getSchedule();
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTalk(@RequestBody @Valid TalkUpdateRequest talkUpdateRequest) {
        talkService.updateTalk(talkUpdateRequest);
        return ResponseEntity.ok("Talk updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTalk(@RequestBody @Valid TalkDeleteRequest deleteTalkRequest) {
        talkService.deleteTalk(deleteTalkRequest);
        return ResponseEntity.ok("Talk deleted successfully");
    }
}
