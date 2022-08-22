package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.response.ResponseParseResult;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;

/**
 * REST-контроллер работы с загрузкой csv-файлов.
 */
@CrossOrigin()
@RestController
@RequestMapping("/upload")
@Slf4j
@AllArgsConstructor
@Data
public class UploadController {

    private final MainService mainService;
    private final UsersRepo usersRepo;
    private final Utils utils;

    /**
     * Загрузить csv-файл, распознать и добавить желания из него.
     * @param principal
     * @param csvFile
     * @return
     * @throws UserNotFoundException
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/parsecsv")
    public ResponseParseResult parseCsv(Principal principal, @RequestParam(required = false, value = "csvfile")
            MultipartFile csvFile) throws Exception {
            LocalUser localUser = utils.getUserFromPrincipal(principal);
            return mainService.parseCsv(csvFile, localUser);
    }
}
