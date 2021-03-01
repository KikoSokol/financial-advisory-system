package sk.stu.fei.uim.bp.application.backend.client.service;

import org.jetbrains.annotations.NotNull;
import sk.stu.fei.uim.bp.application.backend.client.domain.SelfEmployedPerson;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

public interface SelfEmployedPersonService
{
    SelfEmployedPerson addNewSelfEmployedPerson(@NotNull SelfEmployedPerson newSelfEmployedPerson,
                                                @NotNull FileWrapper frontSideOfIdentityCard,
                                                @NotNull FileWrapper backSideOfIdentityCard);

    SelfEmployedPerson addNewSelfEmployedPerson(@NotNull SelfEmployedPerson newSelfEmployedPerson);

    SelfEmployedPerson updateSelfEmplyedPerson(@NotNull SelfEmployedPerson updateSelfEmployedPerson,
                                               @NotNull FileWrapper frontSideOfIdentityCard,
                                               @NotNull FileWrapper backSideOfIdentityCard);


    SelfEmployedPerson updateSelfEmplyedPerson(@NotNull SelfEmployedPerson updateSelfEmployedPerson);
}
