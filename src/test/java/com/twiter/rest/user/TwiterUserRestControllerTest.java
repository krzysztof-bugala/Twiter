package com.twiter.rest.user;

import com.twiter.Twiter;
import com.twiter.entity.message.Message;
import com.twiter.entity.message.TwiterMessage;
import com.twiter.entity.user.TwiterUser;
import com.twiter.entity.user.User;
import com.twiter.repository.user.exception.UserNotFoundException;
import com.twiter.rest.error.Error;
import com.twiter.rest.error.factory.ErrorFactory;
import com.twiter.rest.error.handler.CustomRestExceptionHandler;
import com.twiter.service.message.MessageService;
import com.twiter.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kb on 2017-09-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Twiter.class)
@WebAppConfiguration
public class TwiterUserRestControllerTest {

    private MockMvc mockMvc;

    @MockBean
    @Qualifier("twiterMessageService")
    private UserService userService;

    @MockBean
    @Qualifier("twiterMessageService")
    private MessageService messageService;

    @SpyBean
    private ErrorFactory errorFactory;


    @InjectMocks
    private TwiterUserRestController twiterUserRestController;

    @Autowired
    private CustomRestExceptionHandler customRestExceptionHandler;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(twiterUserRestController)
                .setControllerAdvice(customRestExceptionHandler)
                .build();
    }

    @Test
    public void usersResponse_whenUserExists() throws Exception {
        //given
        User user = new TwiterUser();
        int userId = 1;
        String firstname = "John";
        String surname = "Smith";
        user.setId(userId);
        user.setFirstname(firstname);
        user.setSurname(surname);

        when(userService.getUser(userId)).thenReturn(user);

        //when & then
       mockMvc.perform(get("/api/v1/twiter/users/1")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userId)))
                .andExpect(jsonPath("$.firstname", is(firstname)))
                .andExpect(jsonPath("$.surname", is(surname)));
    }

    @Test
    public void usersResponse_whenUserNotExists() throws Exception {

        String errorMessage = Error.USER_NOT_FOUND;

        when(userService.getUser(28)).thenThrow(new UserNotFoundException());

        mockMvc.perform(get("/api/v1/twiter/users/28")
                .contentType(contentType))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessages[0]", is(errorMessage)));
    }

    @Test
    public void usersMessagesResponse_whenUserHasMassages() throws Exception {

        int userId = 1;

        int firstMessageId = 1;
        String firstMessageContent = "First message Content";
        Date firstMessageCreationDate = new Date();
        Message message1 = new TwiterMessage();
        message1.setId(firstMessageId);
        message1.setContent(firstMessageContent);
        message1.setCreationDate(firstMessageCreationDate);

        int secondMessageId = 2;
        String secondMessageContent = "Second message Content";
        Date secondMessageCreationDate = new Date();
        Message message2 = new TwiterMessage();
        message2.setId(secondMessageId);
        message2.setContent(secondMessageContent);
        message2.setCreationDate(secondMessageCreationDate);

        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        when(messageService.findMessagesByUserId(userId)).thenReturn(messages);

        mockMvc.perform(get("/api/v1/twiter/users/1/messages")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(firstMessageId)))
                .andExpect(jsonPath("$[0].content", is(firstMessageContent)))
                .andExpect(jsonPath("$[0].creationDate", is(firstMessageCreationDate.getTime())))
                .andExpect(jsonPath("$[1].id", is(secondMessageId)))
                .andExpect(jsonPath("$[1].content", is(secondMessageContent)))
                .andExpect(jsonPath("$[1].creationDate", is(secondMessageCreationDate.getTime())));
    }
}



