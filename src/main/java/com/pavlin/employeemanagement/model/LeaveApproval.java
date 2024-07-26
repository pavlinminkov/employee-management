package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "leave_approval")
public class LeaveApproval extends BaseEntity {

  @NotNull
  @Column(name = "approval_date", nullable = false)
  private LocalDate approvalDate;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "leave_id", nullable = false)
  private Leave leave;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  public LeaveApproval() {
  }

  public LocalDate getApprovalDate() {
    return approvalDate;
  }

  public void setApprovalDate(LocalDate approvalDate) {
    this.approvalDate = approvalDate;
  }

  public Leave getLeave() {
    return leave;
  }

  public void setLeave(Leave leave) {
    this.leave = leave;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

}