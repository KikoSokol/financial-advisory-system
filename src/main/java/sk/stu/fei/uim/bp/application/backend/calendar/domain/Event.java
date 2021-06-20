package sk.stu.fei.uim.bp.application.backend.calendar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document("Calendar")
public abstract class Event
{
    @MongoId
    private ObjectId eventId;

    @NotNull
    private ObjectId agent;

    @NotNull
    private LocalDateTime dateTimeOfStart;

    @NotNull
    private LocalDateTime dateTimeOfEnd;

    private String description;

}
