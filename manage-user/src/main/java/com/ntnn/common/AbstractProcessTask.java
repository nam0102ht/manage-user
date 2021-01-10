package com.ntnn.common;

import io.vertx.core.Vertx;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProcessTask extends AbstractTask{
    boolean mStopWhenFail = true; // default

    List<AbstractTask> mWorkerList = new ArrayList<>();


    AbstractProcessTask(String name, Vertx vertx) { super(name, vertx);}

    AbstractProcessTask(String name, Vertx vertx, boolean isStopWhenFail) { super(name, vertx);
        mStopWhenFail = isStopWhenFail;
    }

    protected void clearTask() {
        mWorkerList.clear();
    }


    public AbstractProcessTask addTask(AbstractTask task) {
        mWorkerList.add(task);

        return this;
    }

    public AbstractProcessTask addTasks(List<AbstractTask> tasks) {
        mWorkerList.addAll(tasks);
        return this;
    }

    public String getResultKey() {
        return "MomoProcess";
    }
}
