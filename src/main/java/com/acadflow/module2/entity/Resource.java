package com.acadflow.module2.entity;

import com.acadflow.module1.entity.Subject;
import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String title;
    private String type;
    private String url;

    public Long getId() {
    return id;
}

public Subject getSubject() {
    return subject;
}

public String getTitle() {
    return title;
}

public String getType() {
    return type;
}

public String getUrl() {
    return url;
}

public void setId(Long id) {
    this.id = id;
}

public void setSubject(Subject subject) {
    this.subject = subject;
}

public void setTitle(String title) {
    this.title = title;
}

public void setType(String type) {
    this.type = type;
}

public void setUrl(String url) {
    this.url = url;
}
}