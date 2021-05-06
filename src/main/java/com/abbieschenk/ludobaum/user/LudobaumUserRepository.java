package com.abbieschenk.ludobaum.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface LudobaumUserRepository extends JpaRepository<LudobaumUser, Long> {

    LudobaumUser save(LudobaumUser user);

    LudobaumUser findByName(String name);

    LudobaumUser findByEmail(String email);
}
