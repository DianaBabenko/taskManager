package com.example.taskManager.domain;

import javax.persistence.*;
import java.util.Optional;


@Entity
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String text;

    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    public Task(String text, String tag) {

        this.text = text;
        this.tag = tag;
    }

    public String getProjectName() {
        return project != null ? project.getName() : "<none>";
    }

    public Integer getProjectId() { return project != null ? project.getId() : 0; }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    public Project getProject(){ return project; }

    public void setProject(Project project) { this.project = project; }
}
