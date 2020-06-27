package homework.testsupport;

import homework.filter.HeaderCheckerFilter;
import homework.service.ChatRoomService;
import homework.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
public abstract class BaseTest {
    protected static final String X_ROOM_ID = "X-ROOM-ID";
    protected static final String X_USER_ID = "X-USER-ID";

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserService userService;

    @BeforeEach
    void initialize() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new HeaderCheckerFilter(chatRoomService, userService)).build();
    }
}
