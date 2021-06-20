package sk.stu.fei.uim.bp.application.backend.calendar.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends Event
{
    @NotBlank
    private String name;

    private LocalDate dateOfAddTask;

    private boolean done;

    public Task()
    {
        this.dateOfAddTask = LocalDate.now();
        this.done = false;
    }
}
