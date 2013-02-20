package com.darren.sp.browser.client;

import com.darren.sp.browser.shared.Point;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 */
public interface ShortestPathServiceAsync {
    void calculateShortestPath(Point startPoint, List<Point> points, AsyncCallback<List<Point>> callback);
}
