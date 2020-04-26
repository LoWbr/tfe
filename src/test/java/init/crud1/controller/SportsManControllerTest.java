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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest
@RunWith(SpringRunner.class)
class SportsManControllerTest {

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
    @MockBean
    private DataSource dataSource;


    @Test
    @WithAnonymousUser
    void getSportsMen() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("users"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("allUsers"));

    }

    @Test
    @WithAnonymousUser
    void createSportsMan() throws Exception {

        mockMvc.perform(get("/signUp"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("signUp"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("sportsManForm"));

    }

    @Test
    void saveSportsMan() {
    }

    @Test
    void updateSportsManForm() {
    }

    @Test
    void updateSportsMan() {
    }

    @Test
    void sportsManOwnDetails() {
    }

    @Test
    void getRegisteredEvents() {
    }

    @Test
    @WithMockUser(roles = {"SIMPLY","CONFIRMED", "ADMINISTRATOR"})
    void getAllMessageSent() throws Exception {

        mockMvc.perform(get("/getMessagesSent"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("getMessages"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("messages","status"));

    }

    @Test
    @WithMockUser(roles = {"SIMPLY","CONFIRMED", "ADMINISTRATOR"})
    void getAllReceivedMessages() throws Exception {

        mockMvc.perform(get("/getReceivedMessages"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("getMessages"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("messages","status"));

    }

    @Test
    @WithMockUser(roles = {"SIMPLY","CONFIRMED", "ADMINISTRATOR"})
    void notifications() throws Exception {

        mockMvc.perform(get("/notifications"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("notifications"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("notifications"));

    }

    @Test
    void addContact() {
    }

    @Test
    void removeContact() {
    }

    @Test
    void applyAsConfirmedUser() {
    }
}