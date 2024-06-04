package com.n11.project.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class TalkScheduleEntry {
    private String trackName;
    private Map<String, String> scheduledEntry = new LinkedHashMap<>();
}
