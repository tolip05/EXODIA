package exodia.services;

import exodia.models.service.UserServiceModel;

public interface UserService {
    boolean createUser(UserServiceModel userServiceModel);
    UserServiceModel loginUser(UserServiceModel userServiceModel);
}
