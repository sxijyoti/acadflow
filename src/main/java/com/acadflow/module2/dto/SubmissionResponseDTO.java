package com.acadflow.module2.dto;

import java.time.LocalDateTime;

public class SubmissionResponseDTO {

    public Long id;
    public Long assignmentId;
    public Long userId;
    public String status;
    public LocalDateTime submittedAt;
}