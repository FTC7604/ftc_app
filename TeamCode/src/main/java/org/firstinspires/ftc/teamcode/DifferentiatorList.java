package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ssuri on 1/26/18.
 */

public class DifferentiatorList
{
    private long maxValueMs;
    private HashMap<Long, Double> values;

    public DifferentiatorList(int maxValueMs)
    {
        this.maxValueMs = maxValueMs;
        values = new HashMap<>();
    }

    public void add(double d)
    {
        long currentTime = System.currentTimeMillis();
        values.put(currentTime, d);
    }

    public double derive()
    {
        if(values.size() < 2)
        {
            return -1;
        }

        long leastTime = Long.MAX_VALUE;
        double leastTimeVal = 0;
        long mostTime = Long.MIN_VALUE;
        double maxTimeVal = 0;
        long currentTime = System.currentTimeMillis();

        List<Long> toRemove = new ArrayList<>();
        for(Map.Entry<Long, Double> e : values.entrySet())
        {
            if(currentTime - e.getKey() > maxValueMs)
            {
                toRemove.add(e.getKey());
            }
            else if(e.getKey() < leastTime)
            {
                leastTime = e.getKey();
                leastTimeVal = e.getValue();
            }
            else if(e.getKey() > mostTime)
            {
                mostTime = e.getKey();
                maxTimeVal = e.getValue();
            }
        }

        for(Long l : toRemove)
        {
            values.remove(l);
        }

        return (maxTimeVal - leastTimeVal) - (mostTime - leastTime);
    }
}
