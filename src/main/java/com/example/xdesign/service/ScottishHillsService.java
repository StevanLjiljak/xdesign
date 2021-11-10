package com.example.xdesign.service;

import com.example.xdesign.model.Mountain;
import com.example.xdesign.repository.ScottishHillsRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ScottishHillsService {

  @Autowired
  public ScottishHillsRepository scottishHillsRepository;

  public void uploadScottishHillsFile(MultipartFile file) {

    Mountain mountain;

    try {
      Scanner scanner = new Scanner(this.convert(file));
      while (scanner.hasNext()) {
        String[] columns = scanner.nextLine().split(",");

        mountain = new Mountain(Long.parseLong(columns[0].trim()), columns[1].trim(),
            Integer.parseInt(columns[2].trim()), columns[3].trim());

        scottishHillsRepository.save(mountain);
      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public List<Mountain> searchByHillCategory(String hillCategory, String height,
      String name, Integer size, String minheight, String maxheight) {

    List<Mountain> mountains = scottishHillsRepository.findByHillCategory(hillCategory);
    List<Mountain> sortedMountains = new ArrayList<>();

    if (height.equals("asc") && mountains != null) {
      sortedMountains = mountains.stream()
          .sorted(Comparator.comparingInt(Mountain::getHeight))
          .collect(Collectors.toList());
    } else if (height.equals("desc") && mountains != null) {
      sortedMountains = mountains.stream()
          .sorted(Comparator.comparingInt(Mountain::getHeight).reversed())
          .collect(Collectors.toList());
    }

    //define comparator for sorting list by name
    Comparator<Mountain> compareByName = (Mountain m1, Mountain m2) ->
        m1.getName().compareTo(m2.getName());

    if (name.equals("asc") && mountains != null) {
      sortedMountains = mountains.stream()
          .sorted(compareByName)
          .collect(Collectors.toList());
    } else if (name.equals("desc") && mountains != null) {
      sortedMountains = mountains.stream()
          .sorted(compareByName.reversed())
          .collect(Collectors.toList());
    }

    if (size != 0 && size > 0 && mountains != null) {
      sortedMountains = mountains.stream().limit(size).collect(Collectors.toList());
    }

    if (minheight.equals("min") && mountains != null) {
      Mountain mountain = mountains.stream()
          .min(Comparator.comparing(Mountain::getHeight)).orElseThrow(NoSuchElementException::new);
      sortedMountains.clear();
      sortedMountains.add(mountain);

    }

    if (maxheight.equals("max") && mountains != null) {
      Mountain mountain = mountains.stream()
          .max(Comparator.comparing(Mountain::getHeight)).orElseThrow(NoSuchElementException::new);
      sortedMountains.clear();
      sortedMountains.add(mountain);
    }

    return sortedMountains;
  }

  public File convert(MultipartFile file) throws IOException {
    File convFile = new File(file.getOriginalFilename());
    convFile.createNewFile();
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }

}
