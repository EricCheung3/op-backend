package com.openprice.domain.account.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResetPasswordRequestRepository extends JpaRepository<UserResetPasswordRequest, String> {

    List<UserResetPasswordRequest> findByEmail(final String email);
}
