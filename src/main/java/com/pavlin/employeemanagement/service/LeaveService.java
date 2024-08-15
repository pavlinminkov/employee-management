package com.pavlin.employeemanagement.service;

import com.pavlin.employeemanagement.dto.LeaveInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveResponse;
import com.pavlin.employeemanagement.dto.LeaveUpdateRequest;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaveService {

  LeaveResponse getLeaveById(UUID id);

  Page<LeaveResponse> getAllLeaves(Pageable pageable);

  Page<LeaveResponse> getAllLeavesByEmployeeId(UUID employeeId, Pageable pageable);

  Page<LeaveResponse> getAllLeavesByLeadId(UUID leadId, Pageable pageable);

  UUID createLeave(LeaveInsertRequest leaveInsertRequest);

  void updateLeave(UUID id, LeaveUpdateRequest leaveUpdateRequest);

  void deleteLeave(UUID id);
}
