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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

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
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            content = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            ObjectReader objectReader = csvMapper.readerFor(City.class)
                    .with(csvMapper.schemaFor(City.class).withHeader());

            MappingIterator<City> iterator = objectReader.readValues(content);
            return iterator.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
