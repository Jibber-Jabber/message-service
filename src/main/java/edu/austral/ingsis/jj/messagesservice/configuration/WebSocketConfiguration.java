package edu.austral.ingsis.jj.messagesservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Value("${AUTH_HOST}")
    private String authHost;

    @Value("${AUTH_PORT}")
    private String authPort;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfiguration.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
//                .setInterceptors(httpSessionHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry brokerRegistry) {
        brokerRegistry.enableSimpleBroker( "/user");
        brokerRegistry.setApplicationDestinationPrefixes("/app");
        brokerRegistry.setUserDestinationPrefix("/user");
    }
//    @Bean
//    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
//        return new HandshakeInterceptor() {
//            @Override
//            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws URISyntaxException {
//                if (request instanceof ServletServerHttpRequest) {
//                    ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) request;
//                    HttpServletRequest servletRequest = servletServerRequest.getServletRequest();
//                    Cookie token = WebUtils.getCookie(servletRequest, "jwt");
//                    if (token != null && sendUserServiceRequest(token.getValue()))
//                        attributes.put("token", token.getValue());
//                    else response.setStatusCode(HttpStatus.BAD_REQUEST);
//                }
//                return true;
//            }
//            @Override
//            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//            }
//        };
//    }
//
//    private boolean sendUserServiceRequest(String jwt) throws URISyntaxException {
//        RestTemplate restTemplate = new RestTemplate();
//
//        final String getUserUrl = "http://" + authHost + ":" + authPort + "/api/users/authenticateUser";
//        logger.info("Authenticating with: http://" + authHost + ":" + authPort + "/api/users/authenticateUser");
//
//        URI getUserUri = new URI(getUserUrl);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cookie", "jwt="+jwt);
//        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<Object> response = restTemplate.exchange(getUserUri, HttpMethod.GET, httpEntity, Object.class);
//        return response.getStatusCodeValue() == 200;
//    }

}
