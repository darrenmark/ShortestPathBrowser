package com.darren.sp.browser.client;

import com.darren.sp.browser.shared.Point;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 **/ 
 @RemoteServiceRelativePath("shortestPathService")
public interface ShortestPathService extends RemoteService {
    List<Point> calculateShortestPath(Point startPoint, List<Point> points);
}
