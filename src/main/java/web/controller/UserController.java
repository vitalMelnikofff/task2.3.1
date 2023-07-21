package web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.Model.User;
import web.Service.UserService;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(ModelMap model, HttpSession session) {
        model.addAttribute("users", userService.getAllUsers());
        if (session.getAttribute("message") != null) {
            model.addAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }
        return "users";
    }

    @GetMapping("/create")
    public String showUserCreationPage(ModelMap map) {
        final User user = new User();
        map.addAttribute("user", user);
        return "create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user, ModelMap map) {
        userService.addUser(user);
        final String message = "User was added";
        map.addAttribute("user", new User());
        map.addAttribute("message", message);
        return "create";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id, HttpSession session) {
        userService.deleteUserById(id);
        session.setAttribute("message", "User was deleted");
        return "redirect:/users";
    }

    @GetMapping("/update")
    public String showUpdatePage(@RequestParam("id") Long id, ModelMap map) {
        map.addAttribute("user", userService.getUser(id));
        return "update";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, HttpSession session) {
        userService.updateUser(user);
        session.setAttribute("message", "User was updated");
        return "redirect:/users";
    }
}