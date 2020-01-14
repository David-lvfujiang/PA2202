package com.fenda.onn.utils;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/14
 * @Describe:
 */
public class SpotUtil {
double x;
double y;

public SpotUtil() {
}

public SpotUtil(double x, double y) {
this.x = x;
this.y = y;
}

 // 静态方法
public static double getDis(SpotUtil a, SpotUtil b) {
double c = 0;
double i = Math.pow((a.x - b.x), 2);
double j = Math.pow((a.y - b.y), 2);
c = Math.sqrt(i + j);
return c;
}

public double getDis(SpotUtil a) {
double c = 0;
double i = Math.pow((a.x - this.x), 2);
double j = Math.pow((a.y - this.y), 2);
c = Math.sqrt(i + j);
return c;
}
}
