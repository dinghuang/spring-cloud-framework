package org.dinghuang.oauth.test;

import org.dinghuang.OauthApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = OauthApplication.class)
public class Oauth2MvcTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    //clientId
    private static final String CLIENT_ID = "dinghuang";
    //clientSecret
    private static final String CLIENT_SECRET = "dinghuang";
    //用户名
    private static final String USERNAME = "admin";
    //密码
    private static final String PASSWORD = "123456";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2MvcTest.class);

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();//初始化MockMvc对象,添加Security过滤器链
    }

    public String obtainAccessToken() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("username", USERNAME);
        params.add("password", PASSWORD);

        // @formatter:off

        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        // @formatter:on

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
//        System.out.println(jsonParser.parseMap(resultString).get("access_token").toString());
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }


    @Test
    public void getAccessToken() throws Exception {
        final String accessToken = obtainAccessToken();
        LOGGER.info("access_token={}", accessToken);
    }

    /**
     * 未授权 401
     *
     * @throws Exception
     */
    @Test
    public void UnauthorizedTest() throws Exception {
//        mockMvc.perform(get("/user")).andExpect(status().isUnauthorized());
        ResultActions actions = mockMvc.perform(get("/user"));
        int status = actions.andReturn().getResponse().getStatus();
        Assert.assertTrue(status == HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 禁止访问 403
     *
     * @throws Exception
     */
    @Test
    public void forbiddenTest() throws Exception {
        final String accessToken = obtainAccessToken();
        LOGGER.info("access_token={}", accessToken);
        mockMvc.perform(get("/forbidden").header("Authorization", "bearer " + accessToken)).andExpect(status().isForbidden());
    }

    /**
     * 允许访问 200
     *
     * @throws Exception
     */
    @Test
    public void accessTokenOk() throws Exception {
        final String accessToken = obtainAccessToken();
        LOGGER.info("access_token={}", accessToken);
        mockMvc.perform(get("/user").header("Authorization", "bearer " + accessToken)).andExpect(status().isOk());
    }

    @Test
    public void permitAllTest() throws Exception {
        final String accessToken = obtainAccessToken();
        LOGGER.info("access_token={}", accessToken);
        String content = mockMvc.perform(get("/permitAll"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LOGGER.info(content);
    }

    @Test
    public void permitAllWithTokenTest() throws Exception {
        final String accessToken = obtainAccessToken();
        LOGGER.info("access_token={}", accessToken);
        String content = mockMvc.perform(get("/permitAll").header("Authorization", "bearer " + accessToken + "11"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LOGGER.info(content);
    }
}
