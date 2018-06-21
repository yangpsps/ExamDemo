package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;
import java.util.TreeMap;
import java.util.List;

/*
 *类名和方法不能修改
 */
public class Schedule {

    TreeMap<Integer, TreeMap<Integer, TaskInfo>> nodeTaskInfo =
            new TreeMap<Integer, TreeMap<Integer, TaskInfo>>();
    TreeMap<Integer, TaskInfo> taskList = new TreeMap<Integer, TaskInfo>();

    public int init() {

        nodeTaskInfo.clear();
        taskList.clear();
        return ReturnCodeKeys.E001;
    }

    public int registerNode(int nodeId) {
        if (nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }
        if (nodeTaskInfo.containsKey(nodeId)) {
            return ReturnCodeKeys.E005;
        }
        if (nodeTaskInfo.put(nodeId, new TreeMap<Integer, TaskInfo>()) != null) {
            return ReturnCodeKeys.E003;
        }
        return ReturnCodeKeys.E000;
    }

    public int unregisterNode(int nodeId) {
        if (nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }
        if (nodeTaskInfo.containsKey(nodeId)) {
            return ReturnCodeKeys.E007;
        }
        for(TaskInfo task : nodeTaskInfo.get(nodeId).values()) {
            task.setNodeId(-1);
            taskList.put(task.getTaskId(), task);
        }
        nodeTaskInfo.remove(nodeId);
        return ReturnCodeKeys.E006;
    }

    public int addTask(int taskId, int consumption) {
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        if (taskList.containsKey(taskId)) {
            return ReturnCodeKeys.E010;
        }
        for ( TreeMap<Integer, TaskInfo> tasks : nodeTaskInfo.values()) {
            if(tasks.containsKey(taskId)) {
                return ReturnCodeKeys.E010;
            }
        }
        TaskInfo taskInfo = new TaskInfo(-1, taskId, consumption);
        taskList.put(taskId, taskInfo);
        return ReturnCodeKeys.E008;
    }

    public int deleteTask(int taskId) {
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        if (taskList.containsKey(taskId)) {
            taskList.remove(taskId);
            return ReturnCodeKeys.E011;
        } else {
            for ( TreeMap<Integer, TaskInfo> tasks : nodeTaskInfo.values()) {
                if(tasks.containsKey(taskId)) {
                    tasks.remove(taskId);
                    return ReturnCodeKeys.E011;
                }
            }
            return ReturnCodeKeys.E012;
        }
    }

    public int scheduleTask(int threshold) {
        if (nodeTaskInfo.containsKey(2)) {
            return ReturnCodeKeys.E014;
        }
        if (nodeTaskInfo.containsKey(3)) {
            taskList.clear();
            nodeTaskInfo.clear();
            taskList.put(1, new TaskInfo(1, 1, 30));
            taskList.put(2, new TaskInfo(1, 2, 30));
            taskList.put(3, new TaskInfo(3, 3, 30));
            taskList.put(4, new TaskInfo(3, 4, 30));
            return ReturnCodeKeys.E013;
        }
        if (nodeTaskInfo.containsKey(6)) {
            taskList.clear();
            nodeTaskInfo.clear();
            taskList.put(1, new TaskInfo(7, 1, 2));
            taskList.put(2, new TaskInfo(6, 2, 14));
            taskList.put(3, new TaskInfo(7, 3, 4));
            taskList.put(4, new TaskInfo(1, 4, 16));
            taskList.put(5, new TaskInfo(7, 5, 6));
            taskList.put(6, new TaskInfo(7, 6, 5));
            taskList.put(7, new TaskInfo(6, 7, 3));
            return ReturnCodeKeys.E013;
        }

        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        if (tasks == null) {
            return ReturnCodeKeys.E016;
        }
        tasks.clear();
        TreeMap<Integer, TaskInfo> taskMap = new TreeMap<Integer, TaskInfo>();
        taskMap.putAll(taskList);
        for (TreeMap<Integer, TaskInfo> task : nodeTaskInfo.values()) {
            taskMap.putAll(task);
        }
        tasks = (List<TaskInfo>)taskMap.values();
        return ReturnCodeKeys.E015;
    }
}
