package com.acadflow.ui.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.acadflow.ui.dto.*;
import com.acadflow.ui.util.SessionManager;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import java.util.List;

/**
 * Service layer for API calls
 * Communicates with Spring Boot backend
 */
public class ApiService {
    
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final Gson gson = new Gson();
    private static final HttpClient httpClient = HttpClients.createDefault();

    /**
     * SUBJECT SERVICES
     */
    public static List<SubjectDTO> getAllSubjects() throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/subjects");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, new TypeToken<List<SubjectDTO>>(){}.getType());
        }
    }

    public static List<SubjectDTO> getEnrolledSubjects() throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/subjects/enrolled");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, new TypeToken<List<SubjectDTO>>(){}.getType());
        }
    }

    public static void enrollSubject(Long subjectId) throws IOException, ParseException {
        HttpPost request = new HttpPost(BASE_URL + "/subjects/" + subjectId + "/enroll");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        httpClient.executeOpen(null, request, null).close();
    }

    public static void dropSubject(Long subjectId) throws IOException, ParseException {
        HttpPost request = new HttpPost(BASE_URL + "/subjects/" + subjectId + "/drop");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        httpClient.executeOpen(null, request, null).close();
    }

    /**
     * ASSIGNMENT SERVICES
     */
    public static List<AssignmentDTO> getAssignments() throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/assignments");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, new TypeToken<List<AssignmentDTO>>(){}.getType());
        }
    }

    public static AssignmentDTO getAssignmentById(Long assignmentId) throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/assignments/" + assignmentId);
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, AssignmentDTO.class);
        }
    }

    public static void submitAssignment(Long assignmentId, String filePath) throws IOException, ParseException {
        HttpPost request = new HttpPost(BASE_URL + "/assignments/" + assignmentId + "/submit");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        String json = gson.toJson(new SubmitAssignmentRequest(filePath));
        request.setEntity(new StringEntity(json));
        
        httpClient.executeOpen(null, request, null).close();
    }

    /**
     * EXAM SERVICES
     */
    public static List<ExamDTO> getExams() throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/exams");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, new TypeToken<List<ExamDTO>>(){}.getType());
        }
    }

    /**
     * TIMETABLE SERVICES
     */
    public static List<TimetableSlotDTO> getTimetable() throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/timetable");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, new TypeToken<List<TimetableSlotDTO>>(){}.getType());
        }
    }

    /**
     * HOLIDAY SERVICES
     */
    public static List<HolidayDTO> getHolidays() throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/holidays");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, new TypeToken<List<HolidayDTO>>(){}.getType());
        }
    }

    /**
     * RESOURCE SERVICES
     */
    public static List<ResourceDTO> getResourcesBySubject(String subjectCode) throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/resources?subject=" + subjectCode);
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, new TypeToken<List<ResourceDTO>>(){}.getType());
        }
    }

    /**
     * USER SERVICES
     */
    public static UserDTO getUserProfile() throws IOException, ParseException {
        HttpGet request = new HttpGet(BASE_URL + "/user/profile");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        try (var response = httpClient.executeOpen(null, request, null)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return gson.fromJson(responseBody, UserDTO.class);
        }
    }

    public static void updateUserProfile(UserDTO user) throws IOException, ParseException {
        HttpPost request = new HttpPost(BASE_URL + "/user/profile");
        request.setHeader("Authorization", "Bearer " + SessionManager.getInstance().getAuthToken());
        
        String json = gson.toJson(user);
        request.setEntity(new StringEntity(json));
        
        httpClient.executeOpen(null, request, null).close();
    }

    // Helper class for requests
    private static class SubmitAssignmentRequest {
        String filePath;
        SubmitAssignmentRequest(String filePath) {
            this.filePath = filePath;
        }
    }
}
