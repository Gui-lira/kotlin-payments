package org.study.project.kotlinpayments.controller.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userHandler: UserHandler
) {
    @PostMapping("/create")
    suspend fun createUser(
        @RequestParam document: String,
    ): ResponseEntity<UUID> {
        val userId = userHandler.create(document)
        return ResponseEntity.ok(userId)
    }

    @GetMapping("/id/{userId}")
    suspend fun findByUserId(
        @PathVariable userId: String,
    ): ResponseEntity<String> {
        val userUUID = UUID.fromString(userId)
        val document = userHandler.findById(userUUID) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(document)
    }

    @GetMapping("/document/{document}")
    suspend fun findByDocument(
        @PathVariable document: String,
    ): ResponseEntity<String> {
        val userId = userHandler.findByDocument(document) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(userId.toString())
    }

    @DeleteMapping("/{userId}")
    suspend fun deleteByUserId(
        @PathVariable userId: UUID,
    ): ResponseEntity<Unit> {
        userHandler.deleteById(userId)
        return ResponseEntity.ok().build()
    }
}
