package com.gremio.model.input;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectInput(String name, String key, UserInput projectLead, String description,
                           LocalDateTime startDate, LocalDateTime endDate, String status, String category,
                           List<IssueInput> issues, List<UserInput> teamMembers) {
}