package sk.stu.fei.uim.bp.application.backend.calendar.service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface CalendarInformationService
{
    boolean isFreeThisTime(@NotNull LocalDateTime start, @NotNull LocalDateTime end);
}
