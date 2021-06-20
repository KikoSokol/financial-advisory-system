package sk.stu.fei.uim.bp.application.backend.calendar.repository.implementation;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Event;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Meeting;
import sk.stu.fei.uim.bp.application.backend.calendar.repository.CalendarRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CalendarRepositoryImpl implements CalendarRepository
{

    private MongoOperations mongoOperations;

    @Autowired
    public CalendarRepositoryImpl(MongoTemplate mongoOperations)
    {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Event addEvent(@NotNull Event newEvent)
    {
        return mongoOperations.insert(newEvent);
    }

    @Override
    public Event updateEvent(@NotNull Event updateEvent)
    {
        return mongoOperations.save(updateEvent);
    }

    @Override
    public boolean deleteEvent(@NotNull Event deleteEvent)
    {
        DeleteResult deleteResult = mongoOperations.remove(deleteEvent);
        return deleteResult.wasAcknowledged();
    }

    @Override
    public Optional<Event> getEventById(@NotNull ObjectId eventId)
    {
        Event event = this.mongoOperations.findById(eventId,Event.class);

        return Optional.ofNullable(event);
    }

    @Override
    public List<Event> getAllEventsByCurrentAgent(@NotNull ObjectId currentAgentId)
    {
        Criteria criteria = new Criteria("agent");
        criteria.is(currentAgentId);

        Query query = new Query(criteria);

        return mongoOperations.find(query,Event.class);
    }

    @Override
    public boolean isFreeThisTime(@NotNull LocalDateTime start, @NotNull LocalDateTime end)
    {
        Criteria existsEventIsLongAsNew = new Criteria("dateTimeOfStart");
        existsEventIsLongAsNew.lt(start).and("dateTimeOfEnd").gt(end);

        Criteria existsEventIsBetweenNew = new Criteria("dateTimeOfStart");
        existsEventIsBetweenNew.gte(start).and("dateTimeOfEnd").lte(end);

        Criteria startExistsIsBetweenNew = new Criteria("dateTimeOfStart");
        startExistsIsBetweenNew.gte(start).lt(end);

        Criteria endExistisIsBetweenNew = new Criteria("dateTimeOfEnd");
        endExistisIsBetweenNew.gt(start).lte(end);

        Criteria allCriteria = new Criteria();
        allCriteria.orOperator(existsEventIsBetweenNew,existsEventIsLongAsNew,startExistsIsBetweenNew,endExistisIsBetweenNew);

        Query query = new Query(allCriteria);

        List<Event> events = this.mongoOperations.find(query,Event.class);

        for(Event event : events)
        {
            if(event instanceof Meeting)
                return false;
        }

        return true;
    }



}
