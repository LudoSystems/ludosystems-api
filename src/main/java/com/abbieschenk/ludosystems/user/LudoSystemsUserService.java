package com.abbieschenk.ludosystems.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LudoSystemsUserService implements UserDetailsService {

    private final LudoSystemsUserRepository repository;

    @Autowired
    public LudoSystemsUserService(LudoSystemsUserRepository repository) {
        this.repository = repository;
    }

    /**
     * Get the current logged in {@link LudoSystemsUser}
     *
     * @return The current logged in {@link LudoSystemsUser}
     */
    public LudoSystemsUser getCurrentUser() {
        final String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.loadUserByUsername(name);
    }

    /**
     * Search for {@link LudoSystemsUser} by email.
     *
     * @param email The username of the user to search for
     * @return The {@link LudoSystemsUser}
     */
    public LudoSystemsUser getUserByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    /**
     * Search for {@link LudoSystemsUser} by username. Wrapper method for {@link #loadUserByUsername(String)}.
     *
     * @param name The username of the user to search for
     * @return The {@link LudoSystemsUser}
     */
    public LudoSystemsUser getUserByName(String name) {
        return this.loadUserByUsername(name);
    }

    /**
     * Add and persist a new {@link LudoSystemsUser}.
     *
     * @param user The {@link LudoSystemsUser} to persist.
     * @return The persisted {@link LudoSystemsUser}.
     */
    @Transactional
    public LudoSystemsUser addUser(LudoSystemsUser user) {
        return this.repository.save(user);
    }

    @Override
    public LudoSystemsUser loadUserByUsername(String name) throws UsernameNotFoundException {
        return this.repository.findByName(name);
    }
}
