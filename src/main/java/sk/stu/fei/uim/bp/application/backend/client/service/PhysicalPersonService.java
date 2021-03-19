package sk.stu.fei.uim.bp.application.backend.client.service;

import org.jetbrains.annotations.NotNull;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.util.List;

public interface PhysicalPersonService {

    PhysicalPerson addNewPhysicalPerson(@NotNull PhysicalPerson newPhysicalPerson,
                                        @NotNull FileWrapper frontSideOfIdentityCard,
                                        @NotNull FileWrapper backSideOfIdentityCard);

    PhysicalPerson addNewPhysicalPerson(@NotNull PhysicalPerson newPhysicalPerson);

    PhysicalPerson updatePhysicalPerson(@NotNull PhysicalPerson updatePhysicalPerson);

    PhysicalPerson updatePhysicalPerson(@NotNull PhysicalPerson updatePhysicalPerson,
                                        @NotNull FileWrapper frontSideOfIdentityCard,
                                        @NotNull FileWrapper backSideOfIdentityCard );
}
