package com.abbieschenk.ludobaum.user;

import com.abbieschenk.ludobaum.node.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class SpringDataRestEventHandler {

    private final LudobaumUserRepository userRepository;

    @Autowired
    public SpringDataRestEventHandler(LudobaumUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    public void applyUserInformationUsingSecurityContext(Node node) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        LudobaumUser user = this.userRepository.findByName(name);

        node.setUser(user);
    }
}
