package com.pavlin.employeemanagement.service;

import com.pavlin.employeemanagement.dto.LeaveActionInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveActionResponse;
import com.pavlin.employeemanagement.dto.LeaveActionUpdateRequest;
import java.util.List;
import java.util.UUID;

public interface LeaveActionService {

  LeaveActionResponse getLeaveActionById(UUID id);

  List<LeaveActionResponse> getAllLeaveActions();

  List<LeaveActionResponse> getAllLeaveActionsByLeaveId(UUID leaveId);

  UUID createLeaveAction(LeaveActionInsertRequest leaveActionInsertRequest);

  void updateLeaveAction(UUID id, LeaveActionUpdateRequest leaveActionUpdateRequest);

  void deleteLeaveAction(UUID id);
}
