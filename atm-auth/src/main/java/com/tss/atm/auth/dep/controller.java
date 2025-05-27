
package com.tss.atm.auth.dep;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class controller {
    /*

  @Autowired
  AuthService  authService;

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        return Result.success(authService.register(user));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success();
    }

    @GetMapping("/current-user")
    public Result<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        return Result.success(authService.getCurrentUser(token));
    }
    */
}
