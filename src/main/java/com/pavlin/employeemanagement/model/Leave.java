package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import com.pavlin.employeemanagement.model.common.LeaveState;
import com.pavlin.employeemanagement.model.common.LeaveType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "`leave`")
public class Leave extends BaseEntity {

  @NotNull
  @Column(name = "request_date", nullable = false)
  private LocalDate requestDate;

  @NotNull
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @NotNull
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @NotNull
  @ColumnDefault("'PENDING'")
  @Enumerated(EnumType.STRING)
  @Column(name = "state", nullable = false)
  private LeaveState state;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private LeaveType type;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  @OneToMany(mappedBy = "leave")
  private Set<LeaveAction> leaveActions = new LinkedHashSet<>();

  public LocalDate getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(LocalDate requestDate) {
    this.requestDate = requestDate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LeaveState getState() {
    return state;
  }

  public void setState(LeaveState state) {
    this.state = state;
  }

  public LeaveType getType() {
    return type;
  }

  public void setType(LeaveType type) {
    this.type = type;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Set<LeaveAction> getLeaveActions() {
    return leaveActions;
  }

  public void setLeaveActions(Set<LeaveAction> leaveActions) {
    this.leaveActions = leaveActions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    Leave leave = (Leave) o;
    return getStartDate().equals(leave.getStartDate()) && getEndDate().equals(leave.getEndDate())
        && getEmployee().equals(leave.getEmployee());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getStartDate().hashCode();
    result = 31 * result + getEndDate().hashCode();
    result = 31 * result + getEmployee().hashCode();
    return result;
  }
}