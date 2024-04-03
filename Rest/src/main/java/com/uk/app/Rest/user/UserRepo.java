package com.uk.app.Rest.user;

import com.uk.app.Rest.Models.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<user, Long> {
}
