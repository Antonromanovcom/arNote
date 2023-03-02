package com.antonromanov.arnote.domain.wish.api;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static com.antonromanov.arnote.domain.wish.api.tempzip.AddToZip.copy;
import static com.antonromanov.arnote.domain.wish.api.tempzip.ModZip.patch;
import static com.antonromanov.arnote.domain.wish.api.tempzip.ZipHelper.zip;

@CrossOrigin()
@RestController
@RequestMapping("/zip")
@AllArgsConstructor
public class ZipController {


    @CrossOrigin(origins = "*")
    @GetMapping
    public SingleOperationRs zipIt() throws IOException {

        ClassLoader csd = this.getClass().getClassLoader();
        URL resource = csd.getResource("test.zip");
        URL resource1 = csd.getResource("test.mov");
        String s  = resource.getPath();
        File is = null;
        File is1 = null;

        try {
            is = new File(resource.toURI());
            is1 = new File(resource1.toURI());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

        zip(List.of(is, is1),  1000 * 1000 * 100);
        return SingleOperationRs.builder().id(1L).build();

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/combine")
    public SingleOperationRs combineZips() throws Exception {
        patch();
        return SingleOperationRs.builder().id(1L).build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/add")
    public SingleOperationRs addToZip() {
        copy();
        return SingleOperationRs.builder().id(1L).build();
    }
}
