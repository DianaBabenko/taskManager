package com.example.taskManager;

import com.example.taskManager.domain.Project;
import com.example.taskManager.domain.Task;
import com.example.taskManager.repository.ProjectRepository;
import com.example.taskManager.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/tasks/add")
    public String create(Map<String, Object> model) {

        Iterable<Project> projects = projectRepository.findAll();
        model.put("projects", projects);
        return "add";
    }

    @GetMapping("/tasks/edit")
    public String edit(
            @RequestParam (name="idTaskEdit") Integer idTaskEdit,
            Map<String, Object> model
    ) {
        Optional<Task> task = taskRepository.findById(idTaskEdit);
        Iterable<Project> projects = projectRepository.findAll();

        model.put("idTask", idTaskEdit);
        model.put("tag", task.get().getTag());
        model.put("text", task.get().getText());
        model.put("projectId", task.get().getProjectId());
        model.put("projects", projects);

        return "edit";
    }

    @GetMapping("/tasks")
    public String main(Map<String, Object> model, Map<String, Object> modelProj) {
        Iterable<Task> tasks = taskRepository.findAll();
        model.put("tasks", tasks);

        Iterable<Project> projects = projectRepository.findAll();
        modelProj.put("projects", projects);
        return "task";
    }

    @PostMapping("/tasks/add")
    public RedirectView addTask(
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
        return new RedirectView("");
    }

    @PostMapping("/tasks/edit")
    public RedirectView editTask(
            @RequestParam String updateText,
            @RequestParam String updateTag,
            @RequestParam Integer idTaskEdit,
            @RequestParam Integer projectValue,
            Map<String, Object> model
    ) {
        Optional<Task> task = taskRepository.findById(idTaskEdit);
        Optional<Project> projects = projectRepository.findById(projectValue);

        task.get().setId(idTaskEdit);
        task.get().setText(updateText);
        task.get().setTag(updateTag);
        task.get().setProject(projects.get());
        taskRepository.save(task.get());

        Iterable<Task> tasks = taskRepository.findAll();

        model.put("tasks", tasks);
        return new RedirectView("");
    }

    @PostMapping("/tasks/delete")
    public RedirectView delTask(
            @RequestParam Integer idTaskDel
            //Map<String, Object> model
    ) {
        taskRepository.deleteById(idTaskDel);

//        Iterable<Task> tasks = taskRepository.findAll();
//
//        model.put("tasks", tasks);
        return new RedirectView("");
    }


}