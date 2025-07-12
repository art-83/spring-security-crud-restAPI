package br.devdeloop.uepb.repositories;

import br.devdeloop.uepb.models.AppUser;
import br.devdeloop.uepb.util.AppUserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    public AppUser findByUsername(String username);

    @Modifying
    @Query("""
            UPDATE AppUser u
            SET u.role = :role, u.password = :password
            WHERE u.username = :username
            """)
    public void updateByUsername(@Param("username") String username, @Param("role") AppUserRoleEnum role, @Param("password") String password);
}