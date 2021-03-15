package by.sommelierr.controllers;

import by.sommelierr.models.User;
import by.sommelierr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, Model model) {
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("errorMessage", "Passwords are not the same!");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("errorMessage", "User with this username already exist!");
            return "registration";
        }

        return "redirect:/login";
    }

}
