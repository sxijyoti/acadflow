package com.acadflow.ui.dto;

/**
 * Resource/Syllabus DTO
 */
public class ResourceDTO {
    private Long id;
    private String subjectCode;
    private String subjectName;
    private String resourceName;
    private String resourceType; // PDF, VIDEO, LINK, DOCUMENT
    private String URL;
    private String description;
    private Long uploadedBy;

    public ResourceDTO() {}

    public ResourceDTO(Long id, String subjectCode, String resourceName, String resourceType, String URL) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.URL = URL;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getResourceName() { return resourceName; }
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }

    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }

    public String getURL() { return URL; }
    public void setURL(String URL) { this.URL = URL; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(Long uploadedBy) { this.uploadedBy = uploadedBy; }
}
