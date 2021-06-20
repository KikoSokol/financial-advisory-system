package sk.stu.fei.uim.bp.application.backend.calendar.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;


@EqualsAndHashCode(callSuper = true)
@Data
public class Meeting extends Event
{
    private MeetingAddress address;
    private ObjectId clientId;


}
