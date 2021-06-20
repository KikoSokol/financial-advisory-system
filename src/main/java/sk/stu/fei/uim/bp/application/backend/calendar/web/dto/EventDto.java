package sk.stu.fei.uim.bp.application.backend.calendar.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Event;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class EventDto
{
    @MongoId
    private ObjectId eventId;

    @NotNull
    private ObjectId agent;

    @NotNull(message = "Dátum a čas začiatku musí byť zadaný")
    private LocalDateTime dateTimeOfStart;

    @NotNull(message = "Dátum a čas konca musí byť zadaný")
    private LocalDateTime dateTimeOfEnd;

    private String description;

    public EventDto(Event event)
    {
        setEventId(event.getEventId());
        setAgent(event.getAgent());
        setDateTimeOfStart(event.getDateTimeOfStart());
        setDateTimeOfEnd(event.getDateTimeOfEnd());
        setDescription(event.getDescription());
    }


    protected Event toEvent(Event event)
    {
        event.setEventId(this.eventId);
        event.setAgent(this.agent);
        event.setDateTimeOfStart(this.dateTimeOfStart);
        event.setDateTimeOfEnd(this.dateTimeOfEnd);
        event.setDescription(this.description);

        return event;
    }
}
