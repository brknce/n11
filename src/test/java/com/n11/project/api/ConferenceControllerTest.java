package com.n11.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n11.project.dto.TalkAddRequest;
import com.n11.project.dto.TalkDeleteRequest;
import com.n11.project.dto.TalkUpdateRequest;
import com.n11.project.entity.TalkScheduleEntry;
import com.n11.project.service.ScheduleService;
import com.n11.project.service.TalkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConferenceController.class)
class ConferenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private TalkService talkService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.reset(scheduleService, talkService);
    }

    @Test
    void test_add_talks() throws Exception {
        List<TalkAddRequest.TalkItem> talkItems = new ArrayList<>();
        talkItems.add(new TalkAddRequest.TalkItem("Talk 1", 30));
        TalkAddRequest talkAddRequest = new TalkAddRequest(talkItems);

        mockMvc.perform(post("/api/talks/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(talkAddRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Talks added successfully"));

        verify(talkService, times(1)).addTalks(any(TalkAddRequest.class));
    }

    @Test
    void test_add_talks_validation_failure_for_smaller_than_5_minutes_duration() throws Exception {
        List<TalkAddRequest.TalkItem> talkItems = new ArrayList<>();
        talkItems.add(new TalkAddRequest.TalkItem("", 3));
        TalkAddRequest talkAddRequest = new TalkAddRequest(talkItems);

        mockMvc.perform(post("/api/talks/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(talkAddRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['talks[0].title']").value("Title cannot be blank"))
                .andExpect(jsonPath("$.['talks[0].duration']").value("Duration cannot be smaller 5 minutes"));

        verify(talkService, times(0)).addTalks(any(TalkAddRequest.class));
    }

    @Test
    void test_add_talks_validation_failure_for_bigger_than_240_minutes_duration() throws Exception {
        List<TalkAddRequest.TalkItem> talkItems = new ArrayList<>();
        talkItems.add(new TalkAddRequest.TalkItem("", 250));
        TalkAddRequest talkAddRequest = new TalkAddRequest(talkItems);

        mockMvc.perform(post("/api/talks/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(talkAddRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['talks[0].title']").value("Title cannot be blank"))
                .andExpect(jsonPath("$.['talks[0].duration']").value("Duration cannot be more than 240 minutes"));

        verify(talkService, times(0)).addTalks(any(TalkAddRequest.class));
    }

    @Test
    void test_schedule_task() throws Exception {
        List<TalkScheduleEntry> mockSchedule = new ArrayList<>();
        when(scheduleService.getSchedule()).thenReturn(mockSchedule);

        mockMvc.perform(get("/api/talks/schedule")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(scheduleService, times(1)).getSchedule();
    }

    @Test
    public void update_talk_should_return_ok() throws Exception {
        TalkUpdateRequest request = new TalkUpdateRequest();
        request.setTitle("Introduction to Spring Boot");
        request.setDuration(45);
        request.setNewTitle("Advanced Spring Boot");
        request.setNewDuration(50);

        doNothing().when(talkService).updateTalk(any(TalkUpdateRequest.class));

        mockMvc.perform(put("/api/talks/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Talk updated successfully"));

        verify(talkService, times(1)).updateTalk(any(TalkUpdateRequest.class));
    }


    @Test
    public void update_talk_for_bigger_than_240_minutes_duration() throws Exception {
        TalkUpdateRequest request = new TalkUpdateRequest();
        request.setTitle("");
        request.setDuration(250);
        request.setNewDuration(3);

        doNothing().when(talkService).updateTalk(any(TalkUpdateRequest.class));

        mockMvc.perform(put("/api/talks/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title cannot be blank"))
                .andExpect(jsonPath("$.newTitle").value("NewTitle cannot be blank"))
                .andExpect(jsonPath("$.duration").value("Duration cannot be more than 240 minutes"))
                .andExpect(jsonPath("$.newDuration").value("NewDuration cannot be smaller than 5 minutes"));

        verify(talkService, times(0)).updateTalk(any(TalkUpdateRequest.class));
    }

    @Test
    public void delete_talk_should_return_ok() throws Exception {
        TalkDeleteRequest request = new TalkDeleteRequest();
        request.setTitle("Introduction to Spring Boot");
        request.setDuration(45);

        doNothing().when(talkService).deleteTalk(any(TalkDeleteRequest.class));

        mockMvc.perform(delete("/api/talks/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Talk deleted successfully"));

        verify(talkService, times(1)).deleteTalk(any(TalkDeleteRequest.class));
    }

    @Test
    public void test_delete_talk_for_smaller_than_5_minutes_duration() throws Exception {
        TalkDeleteRequest request = new TalkDeleteRequest();
        request.setTitle("");
        request.setDuration(3);

        doNothing().when(talkService).deleteTalk(any(TalkDeleteRequest.class));

        mockMvc.perform(delete("/api/talks/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title cannot be blank"))
                .andExpect(jsonPath("$.duration").value("Duration cannot be smaller than 5 minutes"));

        verify(talkService, times(0)).deleteTalk(any(TalkDeleteRequest.class));
    }

    @Test
    public void test_delete_talk_for_bigger_than_240_minutes_duration() throws Exception {
        TalkDeleteRequest request = new TalkDeleteRequest();
        request.setDuration(250);

        doNothing().when(talkService).deleteTalk(any(TalkDeleteRequest.class));

        mockMvc.perform(delete("/api/talks/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title cannot be blank"))
                .andExpect(jsonPath("$.duration").value("Duration cannot be more than 240 minutes"));

        verify(talkService, times(0)).deleteTalk(any(TalkDeleteRequest.class));
    }

}