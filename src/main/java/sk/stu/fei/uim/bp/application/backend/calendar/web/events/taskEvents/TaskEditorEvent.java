package sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.TaskDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.TaskEditor;

public abstract class TaskEditorEvent extends ComponentEvent<TaskEditor>
{
    private final TaskDto taskDto;

    public TaskEditorEvent(TaskEditor source, TaskDto taskDto) {
        super(source, false);
        this.taskDto = taskDto;
    }

    public TaskDto getTaskDto() {
        return taskDto;
    }
}
