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
public class Process {
    public String processName;
    public int startTime;
    public int terminateTime;
    public int arrivalTime;
    public int burstTime;
    public static int countOfObjects = 0;

    public Process() {
        countOfObjects++;
    }
}
