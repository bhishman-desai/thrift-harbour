package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.service.UserService;

/** Controller class for handling user-related endpoints. */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<String> hi() {
    return ResponseEntity.ok("Hi from user!");
  }

  /* Get user by id */
  /**
   * Handles GET request to retrieve a user by ID.
   *
   * @param userID The ID of the user to be retrieved.
   * @return A {@code ResponseEntity} containing the retrieved {@code User} object.
   */
  @GetMapping("/users/{userID}")
  public ResponseEntity<User> getUserById(@PathVariable Integer userID) {
    return ResponseEntity.ok(userService.findUserById(userID));
  }
}
