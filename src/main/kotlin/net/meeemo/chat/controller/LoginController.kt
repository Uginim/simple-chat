package net.meeemo.chat.controller

import net.meeemo.chat.service.LoginService
import org.springframework.web.bind.annotation.*


//@RestController
//@RequestMapping("/login/oauth2", produces = ["application/json"])
//@RequestMapping("/login/oauth2", produces = ["application/json"])
@RestController
//@RequestMapping("/auth/oauth2")
@RequestMapping("/login/oauth2")
class LoginController(
    private val loginService: LoginService
) {

    @GetMapping("/code/{registrationId}")
    fun googleLogin(@RequestParam("code", required = true) code: String, @PathVariable registrationId: String) {
        loginService.socialLogin(code, registrationId)
    }
}



