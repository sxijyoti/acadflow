package com.acadflow.module2.strategy;

import com.acadflow.module2.entity.Submission;

public interface SubmissionStrategy {
    void apply(Submission submission);
}