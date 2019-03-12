package exodia.controllers;

import exodia.models.binding.UserLoginBindingModel;
import exodia.models.binding.UserRegisterBindingModel;
import exodia.models.service.UserServiceModel;
import exodia.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public ModelAndView register( ModelAndView modelAndView,HttpSession session){
        if (session.getAttribute("username") != null){
            modelAndView.setViewName("redirect:/home");
        }else{
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel userBindingModel, ModelAndView modelAndView){
       if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
           throw new IllegalArgumentException("The passwords is not equals!");
       }
        if (!this.userService.createUser(this.modelMapper.map(userBindingModel,UserServiceModel.class))){
            throw new IllegalArgumentException("Something field");
        }
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView,HttpSession session){
        if (session.getAttribute("username") != null){
            modelAndView.setViewName("redirect:/home");
        }else{
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
    @PostMapping("/login")
    public ModelAndView loginConfirm(@ModelAttribute UserLoginBindingModel userLoginBindingModel,
                                     ModelAndView modelAndView, HttpSession session){
     UserServiceModel userServiceModel = this.userService.loginUser(this.modelMapper.map(userLoginBindingModel,UserServiceModel.class));
     if (userServiceModel == null){
         throw new IllegalArgumentException("User login failed!");
     }
     session.setAttribute("userId",userServiceModel.getId());
     session.setAttribute("username",userServiceModel.getUsername());
     modelAndView.setViewName("redirect:/home");
     return modelAndView;
    }

    public ModelAndView logout(ModelAndView modelAndView,HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else{
            session.invalidate();
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }
}
