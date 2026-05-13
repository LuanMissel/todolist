package br.com.luanmissel.todolist.task;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/tasks")
@AllArgsConstructor

public class TaskController {
    public final ITaskRepository iTaskRepository;

    @PostMapping ("/")
    public TaskModel create (@RequestBody TaskModel taskModel) {
        System.out.println("Passou pelo filtro, chegou no controller e salvou no banco de dados");
        var task = this.iTaskRepository.save(taskModel);
        return task;
    }
}
