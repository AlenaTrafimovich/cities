package com.lena.application.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.lena.application.model.entity.City;
import com.lena.application.repository.CityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@Component
@ConditionalOnProperty(name = "city.load-on-startup")
public class CsvLoader {
    @Value("${city.filename}")
    private String filename;
    private final CsvMapper csvMapper;
    private final CityRepository repository;

    public CsvLoader(CsvMapper csvMapper, CityRepository repository) {
        this.csvMapper = csvMapper;
        this.repository = repository;
    }

    @PostConstruct
    private void loadCitiesToDb() {
        repository.saveAll(getCitiesFromCsv());
    }

    private List<City> getCitiesFromCsv() {
        String content;
        try {
            File file = ResourceUtils.getFile("classpath:" + filename);
            content = Files.readString(file.toPath());
            if (content != null) {
                ObjectReader reader = csvMapper.readerFor(City.class)
                        .with(csvMapper.schemaFor(City.class).withHeader());
                MappingIterator<City> iterator = reader.readValues(content);
                return iterator.readAll();
            }
            return Collections.emptyList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
