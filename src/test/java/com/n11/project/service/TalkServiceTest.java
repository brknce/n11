package com.n11.project.service;

import com.n11.project.dto.TalkAddRequest;
import com.n11.project.entity.Talk;
import com.n11.project.exception.DuplicateTalkException;
import com.n11.project.repository.TalkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TalkServiceTest {
    @Mock
    private TalkRepository talkRepository;

    @InjectMocks
    private TalkService talkService;

    @Test
    void it_should_add_talks() {
        TalkAddRequest.TalkItem talkItem1 = new TalkAddRequest.TalkItem("Test Talk 1", 30);
        TalkAddRequest.TalkItem talkItem2 = new TalkAddRequest.TalkItem("Test Talk 2", 45);
        TalkAddRequest talkAddRequest = new TalkAddRequest();
        talkAddRequest.setTalks(Arrays.asList(talkItem1, talkItem2));

        when(talkRepository.findByTitleAndDuration(anyString(), anyInt())).thenReturn(Optional.empty());

        talkService.addTalks(talkAddRequest);

        verify(talkRepository, times(1)).saveAll(any());
    }

    @Test
    void it_should_throw_DuplicateTalkException_When_Talk_Already_Exists() {
        TalkAddRequest.TalkItem talkItem1 = new TalkAddRequest.TalkItem("Test Talk 1", 30);
        TalkAddRequest talkAddRequest = new TalkAddRequest();
        talkAddRequest.setTalks(Collections.singletonList(talkItem1));

        when(talkRepository.findByTitleAndDuration(anyString(), anyInt())).thenReturn(Optional.of(new Talk()));

        assertThrows(DuplicateTalkException.class, () -> talkService.addTalks(talkAddRequest));

        verify(talkRepository, never()).saveAll(any());
    }


    @Test
    void it_should_handle_lightning_talks_correctly() {
        TalkAddRequest.TalkItem talkItem1 = new TalkAddRequest.TalkItem("Lightning Talk", 10);
        TalkAddRequest talkAddRequest = new TalkAddRequest();
        talkAddRequest.setTalks(Collections.singletonList(talkItem1));

        when(talkRepository.findByTitleAndDuration(anyString(), anyInt())).thenReturn(Optional.empty());

        talkService.addTalks(talkAddRequest);

        verify(talkRepository, times(1)).saveAll(any());
    }
}