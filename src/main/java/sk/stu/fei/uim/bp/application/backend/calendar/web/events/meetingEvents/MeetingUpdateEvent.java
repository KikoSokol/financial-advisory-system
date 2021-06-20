package sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents;
import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.MeetingDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.MeetingEditor;

public class MeetingUpdateEvent extends MeetingEditorEvent
{
    public MeetingUpdateEvent(MeetingEditor source, MeetingDto meetingDto) {
        super(source, meetingDto);
    }
}
