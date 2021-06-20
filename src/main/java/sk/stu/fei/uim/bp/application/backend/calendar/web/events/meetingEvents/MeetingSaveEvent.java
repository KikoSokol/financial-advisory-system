package sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents;

import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.MeetingDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.MeetingEditor;

public class MeetingSaveEvent extends MeetingEditorEvent
{

    public MeetingSaveEvent(MeetingEditor source, MeetingDto meetingDto) {
        super(source, meetingDto);
    }
}
