package com.aptechph.task_management_system.users.services;

import com.aptechph.task_management_system.users.dto.UserRequest;
import com.aptechph.task_management_system.users.dto.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);
}
