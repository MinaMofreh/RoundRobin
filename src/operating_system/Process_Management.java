/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operating_system;

import java.util.ArrayList;

/**
 *
 * @author andrewnagyeb
 */
public class Process_Management {

    public static ArrayList<ganttProcess> ganttChart = new ArrayList<>();
    public static ArrayList<ganttProcess> tempganttChart = new ArrayList<>();
    public static double averageWaitingTime = 0;
    public static double averageTurnaroundTime = 0;
    public static double averageResponseTime = 0;

    public static void sort(ArrayList<Process> process_list) {
        int arraySize = process_list.size();
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize - 1; j++) {
                if (process_list.get(j).arrivalTime > process_list.get(j + 1).arrivalTime) {
                    swap(j, j + 1, process_list);
                }
            }
        }
    }
    public static ArrayList<ganttProcess> simulateRoundRobin(ArrayList<Process> process,int quantam){
        updateBurstTime(process, quantam);
        calculateWaitingTime();
        calculateTurnaroundTime();
        calculateResponseTime();
        return ganttChart;
    }

    private static void swap(int x, int y, ArrayList<Process> arr) {
        Process temp = arr.get(x);
        arr.set(x, arr.get(y));
        arr.set(y, temp);
    }

    public static void filter(ArrayList<Process> process_list) {
        int j = process_list.size();
        while (j >= 0) {
            for (int i = 0; i < process_list.size(); i++) {
                if (process_list.get(i).burstTime <= 0) {
                    process_list.remove(i);
                }
            }
            j--;
        }
    }

    public static boolean updateBurstTime(ArrayList<Process> process_list, int quantamValue) {
        if (process_list.isEmpty()) {
            return true;
        } else {
            sort(process_list);
            updateTime(process_list, quantamValue);
            constructChart(process_list);
            for (int i = 0; i < process_list.size(); i++) {
                process_list.get(i).burstTime -= quantamValue;
            }
            filter(process_list);
            updateBurstTime(process_list, quantamValue);
        }
        return false;
    }

    public static void constructChart(ArrayList<Process> processingChart) {
        ganttProcess gantt_Process;
        for (int i = 0; i < processingChart.size(); i++) {
            gantt_Process = new ganttProcess(processingChart.get(i).processName, processingChart.get(i).startTime,
                    processingChart.get(i).terminateTime, processingChart.get(i).arrivalTime, processingChart.get(i).burstTime);
            ganttChart.add(gantt_Process);
        }
    }

    public static void updateTime(ArrayList<Process> process_list, int Quantam) {
        for (int i = 0; i < process_list.size(); i++) {
            if (i == 0 && ganttChart.isEmpty()) {
                process_list.get(i).startTime = 0;
            } else if (!ganttChart.isEmpty()) {
                process_list.get(i).startTime = ganttChart.get(ganttChart.size() - 1).endTime;
            }
            if (i > 0) {
                process_list.get(i).startTime = process_list.get(i - 1).terminateTime;
            }
            //-------------- 
            if (process_list.get(i).burstTime >= Quantam) {
                process_list.get(i).terminateTime = process_list.get(i).startTime + Quantam;
            } else if (process_list.get(i).burstTime < Quantam && process_list.get(i).burstTime >= 0) {
                process_list.get(i).terminateTime = process_list.get(i).startTime + process_list.get(i).burstTime;
            } else {
                process_list.get(i).terminateTime = Quantam + process_list.get(i).burstTime;
            }
        }
    }
    public static void calculateWaitingTime() {
        int noOfProcess = Process.countOfObjects;
        for (int i = 1; i <= noOfProcess; i++) {
            String processName = "P" + i;
            for (int j = 0; j < ganttChart.size(); j++) {
                if (ganttChart.get(j).name.equals(processName)) {
                    tempganttChart.add(ganttChart.get(j));
                }
            }
            ganttChart.get(i-1).waitingTime = bridgeWaitingTime(tempganttChart);
            averageWaitingTime += bridgeWaitingTime(tempganttChart);
           
            tempganttChart.clear();
        }
        averageWaitingTime /= noOfProcess;
    }

    private static double bridgeWaitingTime(ArrayList<ganttProcess> pr) {
        double waitingTime;
        waitingTime = 0.0;
        for (int i = 0; i < pr.size(); i++) {
            if (i == 0) {
                waitingTime += (pr.get(i).startTime - pr.get(i).arrivalTime);
            } else {
                waitingTime += (pr.get(i).startTime - pr.get(i - 1).endTime);
            }
        }
        return waitingTime;
    }

    public static void calculateTurnaroundTime() {
        
       int noOfObjects = Process.countOfObjects;
        for (int i = 1; i <= noOfObjects; i++) {
            String processName = "P" + i;
            for (int j = 0; j < ganttChart.size(); j++) {
                if (processName.equals(ganttChart.get(j).name)){
                    tempganttChart.add(ganttChart.get(j));
                }
            }
            ganttChart.get(i-1).turnaroundTime = bridgeTurnaroundTime(tempganttChart);
            averageTurnaroundTime += bridgeTurnaroundTime(tempganttChart);
            tempganttChart.clear();
        }
        averageTurnaroundTime /= noOfObjects;
    }

    private static double bridgeTurnaroundTime(ArrayList<ganttProcess> pr) {
        double turnaroundTime;
        turnaroundTime = pr.get(pr.size() - 1).endTime + pr.get(0).arrivalTime;
        System.out.println(turnaroundTime);
        return turnaroundTime;
    }
    
    public static void calculateResponseTime(){
        int noOfObjects = Process.countOfObjects;
        for (int i = 0; i < noOfObjects; i++) {
            averageResponseTime += ganttChart.get(i).startTime;
        }
        averageResponseTime /= noOfObjects;
    }

}
