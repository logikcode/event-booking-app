package com.bw.reference.controller;


import com.bw.commons.security.constraint.Public;
import com.bw.entity.BwFile;
import com.bw.reference.dao.BwFileRepository;
import com.bw.reference.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor


public class BwFileController {
    private final BwFileRepository bwFileRepository;

    @Public
    @GetMapping("/{id:[0-9]+}")
    public RedirectView downloadFile(@PathVariable("id")
                                             long id, HttpServletResponse response) throws IOException {
        BwFile bwFile = bwFileRepository.findById(id).orElseThrow(() -> new ErrorResponse(400,"File not found",null));
        if (bwFile.getExternalReferencePath()!=null &&org.apache.commons.lang3.StringUtils.isNotBlank(bwFile.getExternalReferencePath())) {
            return new RedirectView(bwFile.getExternalReferencePath());
        }
        response.setContentType(bwFile.getContentType());
        IOUtils.write(bwFile.getData(), response.getOutputStream());
        response.flushBuffer();
        return null;
    }

}
