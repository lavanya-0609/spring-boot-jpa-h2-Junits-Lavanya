package com.ing.spring.jpa.h2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ing.spring.jpa.h2.model.Tutorial;
import com.ing.spring.jpa.h2.repository.TutorialRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TutorialControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TutorialRepository tutorialRepository;

    @Test
    public void testGetAllTutorials() throws Exception {
        // Save a tutorial to the repository
        Tutorial tutorial = new Tutorial("java", "Sample Description", false);
        tutorialRepository.save(tutorial);

        // Perform a GET request to /api/tutorials
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/{id}", tutorial.getId())
        .accept(MediaType.APPLICATION_JSON))
               
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("java"));
    }

    @Test
    public void testGetTutorialById() throws Exception {
        // Save a tutorial to the repository
        Tutorial tutorial = new Tutorial("Sample Tutorial", "Sample Description", false);
        tutorial = tutorialRepository.save(tutorial);

        // Perform a GET request to /api/tutorials/{id}
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/{id}", tutorial.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Sample Tutorial"));
    }

    @Test
    public void testCreateTutorial() throws Exception {
        // Create a new tutorial
        Tutorial newTutorial = new Tutorial("New Tutorial", "New Description", false);

        // Perform a POST request to /api/tutorials
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tutorials")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Tutorial\",\"description\":\"New Description\",\"published\":false}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Tutorial"));
    }
}