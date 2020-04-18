package init.crud1.controller;

import init.crud1.Crud1Application;
import init.crud1.service.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest
@RunWith(SpringRunner.class)
class GlobalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;
    @MockBean
    private SportsManService sportsManService;
    @MockBean
    private ManagementService managementService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private NewsService newsService;

    @Test
    void getHome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("home"));
    }

    @Test
    void signIn() throws Exception {
        this.mockMvc.perform(get("/signIn"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("signIn"));
    }

    @Test
    void contactUs() throws Exception {
        this.mockMvc.perform(get("/contactUs"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("contactUs"));
    }

    @Test
    void about() throws Exception {
        this.mockMvc.perform(get("/about"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("about"));
    }

    @Test
    void search() throws Exception {
        this.mockMvc.perform(get("/search"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("search"))
                .andExpect(model().size(4))
                .andExpect(model().attributeExists("searchActivityForm","allKinds","allLevels","allEvents"));
    }
}