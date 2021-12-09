package ru.sbt.subsidy.dictionaries;

import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubsidyApp {

  public static void main(String[] args) {
    Locale.setDefault(new Locale("ru", "RU"));
    SpringApplication.run(SubsidyApp.class, args);
  }
}

