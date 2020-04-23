package init.crud1.controller;

import init.crud1.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@RunWith(SpringRunner.class)
class ActivityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;
    @MockBean
    private SportsManService sportsManService;
    @MockBean
    private ManagementService managementService;
    @MockBean
    private NewsService newsService;

    @Test
    @WithAnonymousUser
    void getAllEvents() throws Exception {
        mockMvc.perform(get("/events"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("events"))
                .andExpect(model().size(4))
                .andExpect(model().attributeExists("allActivities"));
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED", "ADMINISTRATOR"})
    void createEvent() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("createEvent"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("activityForm","allKinds","allLevels"));
    }

    /*@Test
    @WithAnonymousUser
    void saveEvent() throws Exception {

        mockMvc.perform(post("/saveEvent"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("/events"));

    }*/

    @Test
    @WithAnonymousUser
    void eventDetails() {
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED", "ADMINISTRATOR"})
    void getActivitiesByCreator() throws Exception {

        mockMvc.perform(get("/eventsByCreator"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("ownEvents"))
                .andExpect(model().size(4))
                .andExpect(model().attributeExists("ownCreations"));

    }

    @Test
    void ownEventDetails() {
    }

   /* @Test
    @WithMockUser(roles = {"CONFIRMED", "ADMINISTRATOR"})
    void updateEventForm() throws Exception {

        mockMvc.perform(get("/event/update?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("updateEvent"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("allKinds", "allLevels", "activityForm"));

    }*/

    @Test
    void updateEvent() {
    }

    @Test
    void applyAsCandidate() {
    }

    @Test
    void addUser() {
    }

    @Test
    void removeUser() {
    }

    @Test
    void closeEvent() {
    }
}