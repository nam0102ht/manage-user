package com.ntnn.common;

import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

public class TaskWorkFlow extends AbstractProcessTask {

    public TaskWorkFlow(String name, Vertx vertx) {
        super(name, vertx);
    }

    public TaskWorkFlow(String name, Vertx vertx, boolean isStopWhenFail) {
        super(name, vertx, isStopWhenFail);
    }

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        serialRun(this.mWorkerList, input, whenDone);
    }

    protected void serialRun(final List<AbstractTask> taskList, final TaskData input, final Handler<TaskData> whenAllDone) {
        runOneTask(taskList, input, whenAllDone, 0);
    }

    private void runOneTask(List<AbstractTask> taskList, TaskData input, Handler<TaskData> whenAllDone, final int step) {
        if (step == taskList.size() || (mStopWhenFail && input != null && !input.getResult())) {
            //this is the end => do return here
            whenAllDone.handle(input);
        } else {
            AbstractTask task = taskList.get(step);
            task.run(input, entries -> runOneTask(taskList, entries, whenAllDone, step + 1));
        }

    }
}
