package sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents;

import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.TaskDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.TaskEditor;

public class TaskSaveEvent extends TaskEditorEvent
{

    public TaskSaveEvent(TaskEditor source, TaskDto taskDto) {
        super(source, taskDto);
    }
}
