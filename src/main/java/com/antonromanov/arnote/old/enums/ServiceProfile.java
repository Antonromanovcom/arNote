package com.antonromanov.arnote.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Типы одношагового перемещения.
 */
@AllArgsConstructor
@Getter
public enum ServiceProfile {
    UP,
    DOWN;

 //   private String uiValue;
  //  private final ArnoteOperation operation;
}



 /*  if (dto.getStep() == StepType.DOWN) {
            if (mainService.getWishById(dto.getId()).get().getPriority() > 1) { //todo: с этим пиздецом надо что-то думать
                (mainService.getWishById(dto.getId()).get())
                        .setPriority((mainService.getWishById(dto.getId()).get()).getPriority() - 1);
            }
            return mainService.updateWish(mainService.getWishById(dto.getId()).get());
        }

        (mainService.getWishById(dto.getId()).get())
                .setPriority((mainService.getWishById(dto.getId()).get()).getPriority() + 1);
        return mainService.updateWish(mainService.getWishById(dto.getId()).get());
    }*/
