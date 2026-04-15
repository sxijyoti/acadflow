package com.acadflow.module1.controller;

import com.acadflow.module1.dto.SubjectCreateDTO;
import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/{adminId}/subjects")
    public ResponseEntity<Subject> createSubject(@PathVariable Long adminId, @RequestBody SubjectCreateDTO dto) {
        return ResponseEntity.ok(adminService.createSubject(adminId, dto));
    }
}
