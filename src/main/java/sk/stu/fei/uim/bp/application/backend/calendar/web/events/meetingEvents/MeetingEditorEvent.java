package sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.MeetingDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.MeetingEditor;


public abstract class MeetingEditorEvent extends ComponentEvent<MeetingEditor>
{
    private final MeetingDto meetingDto;

    public MeetingEditorEvent(MeetingEditor source, MeetingDto meetingDto) {
        super(source, false);
        this.meetingDto = meetingDto;
    }

    public MeetingDto getMeetingDto() {
        return meetingDto;
    }
}
