package com.example.taskManager;
import com.example.taskManager.domain.Task;
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

    @GetMapping("/tasks/add")
    public String create() {
        return "add";
    }

    @GetMapping("/tasks/edit")
    public String edit(
            @RequestParam (name="idTaskEdit") Integer idTaskEdit,
            Map<String, Object> model
    ) {
        Optional<Task> task = taskRepository.findById(idTaskEdit);

        model.put("idTask", idTaskEdit);
        model.put("tag", task.get().getTag());
        model.put("text", task.get().getText());
        model.put("description", task.get().getDescription());

        return "edit";
    }

    @GetMapping("/tasks")
    public String main(Map<String, Object> model) {
        Iterable<Task> tasks = taskRepository.findAll();
        model.put("tasks", tasks);

        return "task";
    }

    @PostMapping("/tasks/add")
    public RedirectView addTask(
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam String description,
            Map<String, Object> model
    ) {

        Task task = new Task(text, tag);
        task.setDescription(description);
        taskRepository.save(task);

        Iterable<Task> tasks = taskRepository.findAll();

        model.put("tasks", tasks);
        return new RedirectView("");
    }

    @PostMapping("/tasks/edit")
    public RedirectView editTask(
            @RequestParam String updateText,
            @RequestParam String updateTag,
            @RequestParam String updateDescription,
            @RequestParam Integer idTaskEdit,
            Map<String, Object> model
    ) {
        Optional<Task> task = taskRepository.findById(idTaskEdit);

        task.get().setId(idTaskEdit);
        task.get().setText(updateText);
        task.get().setTag(updateTag);
        task.get().setDescription(updateDescription);

        taskRepository.save(task.get());

        Iterable<Task> tasks = taskRepository.findAll();

        model.put("tasks", tasks);
        return new RedirectView("");
    }

    @PostMapping("/tasks/delete")
    public RedirectView delTask(
            @RequestParam Integer idTaskDel
    ) {
        taskRepository.deleteById(idTaskDel);

        return new RedirectView("");
    }
}