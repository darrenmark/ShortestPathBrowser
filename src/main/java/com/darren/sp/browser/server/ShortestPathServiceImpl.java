package com.darren.sp.browser.server;

import com.darren.sp.BruteForcePathCalculator;
import com.darren.sp.Point;
import com.darren.sp.ThreadFactory;
import com.darren.sp.browser.client.ShortestPathService;
import com.google.appengine.api.ThreadManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ShortestPathServiceImpl extends RemoteServiceServlet implements ShortestPathService {

    public List<com.darren.sp.browser.shared.Point> calculateShortestPath(com.darren.sp.browser.shared.Point startPoint, List<com.darren.sp.browser.shared.Point> points) {
        BruteForcePathCalculator<Point> pathCalculator = new BruteForcePathCalculator<Point>();
        List<Point> result = pathCalculator.shortestPathSequence(convert(startPoint), convertTo(points));
        return convertFrom(result);
    }

    private List<com.darren.sp.browser.shared.Point> convertFrom(List<Point> points) {
        List<com.darren.sp.browser.shared.Point> result = new ArrayList<com.darren.sp.browser.shared.Point>();
        for(Point point: points) {
            result.add(convert(point));
        }
        return result;
    }

    private List<Point> convertTo(List<com.darren.sp.browser.shared.Point> points) {
        List<Point> result = new ArrayList<Point>();
        for(com.darren.sp.browser.shared.Point point: points) {
            result.add(convert(point));
        }
        return result;
    }

    private com.darren.sp.browser.shared.Point convert(Point point) {
        return new com.darren.sp.browser.shared.Point((int) point.getX(), (int) point.getY());
    }

    private Point convert(com.darren.sp.browser.shared.Point point) {
        return new Point(point.getX(),point.getY());
    }
}
