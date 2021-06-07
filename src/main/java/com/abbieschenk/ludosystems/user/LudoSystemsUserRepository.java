package com.abbieschenk.ludosystems.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface LudoSystemsUserRepository extends JpaRepository<LudoSystemsUser, Long> {

    LudoSystemsUser save(LudoSystemsUser user);

    LudoSystemsUser findByName(String name);

    LudoSystemsUser findByEmail(String email);
}
