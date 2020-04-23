package init.crud1.controller;

import init.crud1.service.ActivityService;
import init.crud1.service.ManagementService;
import init.crud1.service.NewsService;
import init.crud1.service.SportsManService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest
@RunWith(SpringRunner.class)
class ManagementControllerTest {

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
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void manageUserSetting() throws Exception {

        mockMvc.perform(get("/manageUsers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("manageUsers"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("topicForm","allUsers",
                        "allCandidates"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void manageEventSetting() throws Exception {

        mockMvc.perform(get("/manageEvents"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("manageEvents"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("allActivities"));

    }

    @Test
    void open() {
    }

    @Test
    void block() {
    }

    @Test
    void unblock() {
    }

    @Test
    void refusePromotionUser() {
    }

    @Test
    void promoteUser() {
    }

    @Test
    void createTopic() {
    }

    @Test
    void manageSportsSetting() {
    }

    @Test
    void updateType() {
    }

    @Test
    void addType() {
    }

    @Test
    void manageLevelsSetting() {
    }

    @Test
    void updateLevel() {
    }

    @Test
    void getHistory() {
    }
}