package com.example.xdesign.controler;

import com.example.xdesign.file.UploadFileResponse;
import com.example.xdesign.model.Mountain;
import com.example.xdesign.service.ScottishHillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScottishHillsController {

    @Autowired
    public ScottishHillsService scottishHillsService;

    @PostMapping("/uploadScottishHillsFile")
    public UploadFileResponse uploadScottishHillsFile(@RequestParam("file") MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploadScottishHillsFile/")
                .path(fileName)
                .toUriString();

        scottishHillsService.uploadScottishHillsFile(file);

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    /*
    Filtering of search by hill category (i.e. Munro, Munro Top or none).
    If this information is not provided by the caller, it should default to none.
    */

    @GetMapping("/searchByHillCategory")
    public List<Mountain> searchByHillCategory(@RequestParam(name = "hillCategory") String hillCategory,
                                               @RequestParam(name = "height", required = false) String height,
                                               @RequestParam(name = "name", required = false) String name,
                                               @RequestParam(name = "size", required = false) Integer size,
                                               @RequestParam(name = "minheight", required = false) String minheight,
                                               @RequestParam(name = "maxheight", required = false) String maxheight
        ) {
        return scottishHillsService.searchByHillCategory(hillCategory, height, name, size, minheight, maxheight);
    }

}
