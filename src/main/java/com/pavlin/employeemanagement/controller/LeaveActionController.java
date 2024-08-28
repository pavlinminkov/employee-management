package com.pavlin.employeemanagement.controller;

import com.pavlin.employeemanagement.dto.LeaveActionInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveActionResponse;
import com.pavlin.employeemanagement.dto.LeaveActionUpdateRequest;
import com.pavlin.employeemanagement.service.LeaveActionService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class LeaveActionController {

  private final LeaveActionService leaveActionService;

  public LeaveActionController(LeaveActionService leaveActionService) {
    this.leaveActionService = leaveActionService;
  }

  @GetMapping("/leave-actions/{id}")
  public ResponseEntity<LeaveActionResponse> getLeaveActionById(@PathVariable UUID id) {
    return ResponseEntity.ok(leaveActionService.getLeaveActionById(id));
  }

  @GetMapping("/leave-actions")
  public ResponseEntity<List<LeaveActionResponse>> getLeaveActions() {
    return ResponseEntity.ok(leaveActionService.getAllLeaveActions());
  }

  @GetMapping("/leaves/{leaveId}/leave-actions")
  public ResponseEntity<List<LeaveActionResponse>> getAllLeaveActionsByLeaveId(
      @PathVariable UUID leaveId) {
    return ResponseEntity.ok(leaveActionService.getAllLeaveActionsByLeaveId(leaveId));
  }

  @PostMapping("/leave-actions")
  public ResponseEntity<Void> createLeaveAction(
      @Valid @RequestBody LeaveActionInsertRequest leaveActionInsertRequest) {
    UUID leaveActionId = leaveActionService.createLeaveAction(leaveActionInsertRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(leaveActionId)
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/leave-actions/{id}")
  public ResponseEntity<Void> updateLeaveAction(
      @PathVariable UUID id,
      @Valid @RequestBody LeaveActionUpdateRequest leaveActionUpdateRequest
  ) {
    leaveActionService.updateLeaveAction(id, leaveActionUpdateRequest);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/leave-actions/{id}")
  public ResponseEntity<Void> deleteLeaveAction(@PathVariable UUID id) {
    leaveActionService.deleteLeaveAction(id);

    return ResponseEntity.noContent().build();
  }
}
