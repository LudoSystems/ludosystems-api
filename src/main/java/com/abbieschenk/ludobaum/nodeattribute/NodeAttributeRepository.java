package com.abbieschenk.ludobaum.nodeattribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('USER')")
@NoRepositoryBean
public interface NodeAttributeRepository<T extends NodeAttribute> extends JpaRepository<T, Long> {

    @Override
    @PostAuthorize("returnObject.node?.user?.name == authentication?.name")
    T getOne(Long id);

    @Override
    @PreAuthorize("#attribute?.node?.user?.name == authentication?.name")
    <S extends T> S save(@Param("attribute") S entity);


    @Override
    @PreAuthorize("#attribute?.node?.user?.name == authentication?.name")
    void delete(@Param("attribute") T attribute);

}
