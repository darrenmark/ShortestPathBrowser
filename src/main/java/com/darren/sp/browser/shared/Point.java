package com.darren.sp.browser.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 */
public class Point implements IsSerializable {
    private int x, y;
    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
