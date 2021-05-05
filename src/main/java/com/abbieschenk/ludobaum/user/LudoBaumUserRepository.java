package com.abbieschenk.ludobaum.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface LudoBaumUserRepository extends JpaRepository<LudobaumUser, Long> {

    LudobaumUser save(LudobaumUser ludobaumUser);

    LudobaumUser findByName(String name);

    LudobaumUser findByEmail(String email);
}
