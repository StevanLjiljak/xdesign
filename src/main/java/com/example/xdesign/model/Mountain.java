package com.example.xdesign.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "mountain")
@Table(name = "mountain")
public class Mountain implements Serializable {

  @Id
  public Long id;

  public String name;

  public Integer height;

  public String hillCategory;

  public Mountain() { }

  public Mountain(Long id, String name, Integer height, String hillCategory) {
    this.id = id;
    this.name = name;
    this.height = height;
    this.hillCategory = hillCategory;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public String getHillCategory() {
    return hillCategory;
  }

  public void setHillCategory(String hillCategory) {
    this.hillCategory = hillCategory;
  }
}
