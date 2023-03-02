package com.antonromanov.arnote.domain.finplanning.freeze.service.impl;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.finplanning.freeze.dto.rq.FreezeRq;
import com.antonromanov.arnote.domain.finplanning.freeze.entity.Freeze;
import com.antonromanov.arnote.domain.finplanning.freeze.mapper.FreezeMapper;
import com.antonromanov.arnote.domain.finplanning.freeze.repo.FreezeRepo;
import com.antonromanov.arnote.domain.finplanning.freeze.service.FreezeService;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.old.exceptions.FinPlanningException;
import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;
import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static com.antonromanov.arnote.old.utils.Utils.dateToLocalDate;

@Service
@AllArgsConstructor
public class FreezeServiceImpl implements FreezeService {

    private final FreezeRepo freezeRepo;
    private final UserService userService;
    private final FreezeMapper mapper;


    /**
     * Отфильтровать список фризов по году и месяцу.
     *
     * @param year
     * @param month
     * @return
     */
    public Optional<Freeze> filterFreezeListByDate(int year, int month) {
        ArNoteUser user = userService.getUserFromPrincipal();
        List<Freeze> allFreezesByUser = freezeRepo.findAllByUser(user);
        return allFreezesByUser.stream().filter(e -> dateToLocalDate(e.getStartDate()).withDayOfMonth(1)
                .isEqual(LocalDate.of(year, month, 1))).findFirst();
    }


    @Override
    public Boolean isThisFreeze(int year, int month) {
        ArNoteUser user = userService.getUserFromPrincipal();
        return (freezeRepo.findFreezeByUserAndMonthAndYear(user, year, month)).isPresent();
    }

    @Override
    public SingleOperationRs addFreeze(FreezeRq request) {
        ArNoteUser user = userService.getUserFromPrincipal();
        Optional<Freeze> currentFreeze = freezeRepo.findFreezeByUserAndMonthAndYear(user, request.getYear(), request.getMonth());

        if (currentFreeze.isPresent()) {
            throw new FinPlanningException(ErrorCodes.ERR_16);
        }

        Freeze newFreeze = freezeRepo.saveAndFlush(mapper.map(request, user));
        return SingleOperationRs.builder()
                .id(newFreeze.getId())
                .build();
    }

    @Override
    public SingleOperationRs deleteFreeze(Integer year, Integer month) {
        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
            Optional<Freeze> freeze = freezeRepo.findFreezeByUserAndMonthAndYear(arNoteUser, year, month);
            freeze.ifPresent(freezeRepo::delete);
            return SingleOperationRs.builder()
                    .build();
    }
}
