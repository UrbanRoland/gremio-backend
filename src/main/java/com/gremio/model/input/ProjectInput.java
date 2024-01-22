package com.gremio.model.input;

import com.gremio.persistence.entity.User;

import java.time.LocalDateTime;

public record ProjectInput(String name, String key, User projectLead, String description,
                    LocalDateTime startDate, LocalDateTime endDate, String status, String category) {
}