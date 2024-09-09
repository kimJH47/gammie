package core.gammieapi.presentation.http

import core.gammieapi.admin.application.AdminService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminController(
    private val adminService: AdminService
) {

    @PostMapping("/api/admin/add")
    fun add() {
        adminService.addGames()
    }
}