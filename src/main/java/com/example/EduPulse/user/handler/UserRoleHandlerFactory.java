package com.example.EduPulse.user.handler;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserRoleHandlerFactory {


    private final Map<String, UserHandler> handlerMap;


    public UserRoleHandlerFactory(List<UserHandler> handlers) {
        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(
                        h -> h.getRoleName().toUpperCase(),
                        Function.identity()
                ));
    }

    public UserHandler getHandler(String roleName) {
        UserHandler handler = handlerMap.get(roleName.toUpperCase());

        if (handler == null) {
            throw new RuntimeException("No handler found for role: " + roleName);
        }

        return handler;
    }

}
