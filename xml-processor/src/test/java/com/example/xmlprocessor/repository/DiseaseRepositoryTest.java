package com.example.xmlprocessor.repository;

import com.example.xmlprocessor.model.Disease;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiseaseRepositoryTest {
    // unit test

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DiseaseRepository diseaseRepository;

    private List<Disease> diseases = new ArrayList<>();

    private Disease d;

    @Before
    public void setup()  {
        diseaseRepository.deleteAll();

        this.diseases.add(diseaseRepository.save(new Disease("long_legs")));
        this.diseases.add(diseaseRepository.save(new Disease("nice_to_people")));
        this.diseases.add(diseaseRepository.save(new Disease("used_to_have_dredds")));
        this.diseases.add(diseaseRepository.save(new Disease("chocaholic")));
        this.diseases.add(diseaseRepository.save(new Disease("great_haircut")));
    }

    @Test
    public void testFindAll() {
        List<Disease> dis = diseaseRepository.findAll();
        Assert.assertEquals(diseases.size(), dis.size());
    }

    @Test
    public void testSave() {
        d = new Disease("ebola");
        Disease saved = diseaseRepository.save(d);
        diseaseRepository.flush();

        Assert.assertEquals(saved.getName(), "ebola");
        String name = jdbcTemplate.queryForObject("SELECT name FROM disease WHERE name = ?",
                String.class,
                saved.getName());
        Assert.assertEquals(name, "ebola");
    }

    @Test
    public void testFindByName() {
        d = new Disease("hiv");
        diseaseRepository.save(d);

        Assert.assertNotNull(diseaseRepository.findByName("hiv"));
        Assert.assertEquals(d.getName(), diseaseRepository.findByName("hiv").getName());
        Assert.assertNull(diseaseRepository.findByName("test"));
    }

    @Test
    public void testUpdate() {
        Disease nice_to_people = diseaseRepository.findByName("nice_to_people");
        nice_to_people.setName("Nice to people");

        Disease updated = diseaseRepository.saveAndFlush(nice_to_people);

        Assert.assertEquals(updated.getName(), "Nice to people");

        String name = jdbcTemplate.queryForObject("SELECT name FROM disease WHERE name = ?",
                String.class,
                updated.getName());
        Assert.assertEquals(name, "Nice to people");
    }

}
