package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import com.pavlin.employeemanagement.model.common.LeaveActionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "leave_action",
    uniqueConstraints = @UniqueConstraint(columnNames = {"leave_id", "employee_id"}))
public class LeaveAction extends BaseEntity {

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private LeaveActionType type;

  @NotNull
  @Column(name = "date", nullable = false)
  private LocalDate date;

  // TODO Use the id not the object for equals and hashcode
  @NotNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "leave_id", nullable = false)
  private Leave leave;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  public LeaveActionType getType() {
    return type;
  }

  public void setType(LeaveActionType type) {
    this.type = type;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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

    LeaveAction that = (LeaveAction) o;
    return getType() == that.getType() && getDate().equals(that.getDate()) && getLeave().equals(
        that.getLeave()) && getEmployee().equals(that.getEmployee());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getType().hashCode();
    result = 31 * result + getDate().hashCode();
    result = 31 * result + getLeave().hashCode();
    result = 31 * result + getEmployee().hashCode();
    return result;
  }
}