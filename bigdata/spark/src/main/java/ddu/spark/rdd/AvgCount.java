package ddu.spark.rdd;

import java.io.Serializable;

/**
 * Created by lideyou on 16/5/22.
 * AvgCount
 */
public class AvgCount implements Serializable {
    int total;
    int num;

    AvgCount(int total, int num) {
        this.total = total;
        this.num = num;
    }

    double avg() {
        return total / (double) num;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
