package com.springapp.myapp.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"addresses"})
    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"addresses"})
    @Query("select u from user u")
    List<User> getAllWithAddresses();

    @Procedure("findUsersWithLoyaltyPointGreaterThan")
    List<UserSummaryDto>
    getUsersSummaryWithLoyaltyPointsGreaterThan(Integer LoyaltyPoint);

    @Query("select u.id as id, u.email as email from User u left join Profile p on p.id = u.id where p.loyaltyPoints > :loyaltyPoints order by u.email")
    List<UserSummaryDto> getLoyalUsers(@Param("loyaltyPoints") int loyaltyPoints);

    void deleteBId(Long id);

    boolean existsUserByEmail(String email);

    Optional<User> findUserById(Long id);
}
