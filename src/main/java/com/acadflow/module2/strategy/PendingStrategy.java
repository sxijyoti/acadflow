package com.acadflow.module2.strategy;

import com.acadflow.module2.entity.Submission;
import com.acadflow.module2.entity.SubmissionStatus;

public class PendingStrategy implements SubmissionStrategy {
    public void apply(Submission submission) {
        submission.setStatus(SubmissionStatus.PENDING);
    }
}