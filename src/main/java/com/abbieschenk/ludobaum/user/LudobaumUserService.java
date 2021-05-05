package com.abbieschenk.ludobaum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LudobaumUserService implements UserDetailsService {

    private final LudoBaumUserRepository repository;

    @Autowired
    public LudobaumUserService(LudoBaumUserRepository repository) {
        this.repository = repository;
    }

    /**
     * Search for {@link LudobaumUser} by email.
     *
     * @param email The username of the user to search for
     * @return The {@link LudobaumUser}
     */
    public LudobaumUser getUserByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    /**
     * Search for {@link LudobaumUser} by username. Wrapper method for {@link #loadUserByUsername(String)}.
     *
     * @param name The username of the user to search for
     * @return The {@link LudobaumUser}
     */
    public LudobaumUser getUserByName(String name) {
        return this.loadUserByUsername(name);
    }

    /**
     * Add and persist a new {@link LudobaumUser}.
     *
     * @param user The {@link LudobaumUser} to persist.
     * @return The persisted {@link LudobaumUser}.
     */
    @Transactional
    public LudobaumUser addUser(LudobaumUser user) {
        return this.repository.save(user);
    }

    @Override
    public LudobaumUser loadUserByUsername(String name) throws UsernameNotFoundException {
        return this.repository.findByName(name);
    }
}
