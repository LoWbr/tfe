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

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest
@RunWith(SpringRunner.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ActivityService activityService;
    @MockBean
    SportsManService sportsManService;
    @MockBean
    NewsService newsService;
    @MockBean
    private ManagementService managementService;
    @MockBean
    private DataSource dataSource;

    @Test
    @WithMockUser(roles = {"SIMPLY","CONFIRMED", "ADMINISTRATOR"})
    void createComment() throws Exception {

        /*mockMvc.perform(get("/createComment")
                .param("id","1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("createComment"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("current_activity","commentForm"));*/

    }

    @Test
    void addComment() {
    }
}