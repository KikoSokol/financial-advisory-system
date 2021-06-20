package sk.stu.fei.uim.bp.application.backend.calendar.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Task;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskDto extends EventDto
{
    @NotBlank
    private String name;

    private LocalDate dateOfAddTask;

    private boolean done;

    public TaskDto(Task task)
    {
        super(task);
        setName(task.getName());
        setDateOfAddTask(task.getDateOfAddTask());
        setDone(task.isDone());
    }

    public Task toTask(Task task)
    {
        super.toEvent(task);
        task.setName(this.name);
        task.setDone(this.done);

        return task;
    }
}
