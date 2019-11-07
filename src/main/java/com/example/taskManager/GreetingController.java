package com.example.taskManager;

import com.example.taskManager.domain.Project;
import com.example.taskManager.domain.Task;
import com.example.taskManager.repository.ProjectRepository;
import com.example.taskManager.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Optional;

@Controller
public class GreetingController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model, Map<String, Object> modelProj) {
        Iterable<Task> tasks = taskRepository.findAll();
        model.put("tasks", tasks);

        Iterable<Project> projects = projectRepository.findAll();
        modelProj.put("projects", projects);
        return "main";
    }

    @PostMapping
    public String add(
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam Integer projectValue,
            Map<String, Object> model
    ) {
       Optional<Project> projects = projectRepository.findById(projectValue);

        Task task = new Task(text, tag);
        task.setProject(projects.get());
        taskRepository.save(task);

        Iterable<Task> tasks = taskRepository.findAll();

        model.put("tasks", tasks);
        return "main";
    }

}