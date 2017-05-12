package operating_system;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andrewnagyeb
 */
public class ganttProcess {
    String name;
    int startTime;
    int endTime;
    int arrivalTime;
    int burstTime;
    double waitingTime;
    double turnaroundTime;
    double responseTime;

    public ganttProcess(String name, int startTime, int endTime,int arrivalTime,int burstTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
    
    
}
