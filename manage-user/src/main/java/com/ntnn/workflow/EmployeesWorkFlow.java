package com.ntnn.workflow;

import com.ntnn.common.TaskWorkFlow;
import com.ntnn.task.CEODBTask;
import com.ntnn.task.DepartmentManagerDBTask;
import com.ntnn.task.EmployeeTeamsDBTask;
import com.ntnn.task.TeamsDepartmentDBTask;
import io.vertx.core.Vertx;

public class EmployeesWorkFlow extends TaskWorkFlow {

    public EmployeesWorkFlow(String name, Vertx vertx) {
        super(name, vertx);
        addTask(new CEODBTask("CEODBTask", vertx));
        addTask(new DepartmentManagerDBTask("DepartmentManagerDBTask", vertx));
        addTask(new TeamsDepartmentDBTask("TeamsDepartmentDBTask", vertx));
        addTask(new EmployeeTeamsDBTask("EmployeeTeamsDBTask", vertx));
    }
}
