package com.darren.sp.browser.client;

import com.darren.sp.browser.shared.Point;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;

import java.util.ArrayList;
import java.util.List;

/**
 */
class CanvasMap implements IsWidget{
    private Canvas canvas;
    private Context2d context;
    private List<Point> canvasRelativePoints = new ArrayList<Point>();
    private boolean linesHaveBeenDrawn = false;
    private TextButton generateButton;

    CanvasMap(TextButton generateButton) {
        this.generateButton = generateButton;
    }

    public Widget asWidget() {
        return getCanvas();
    }

    private Canvas getCanvas() {
        if(canvas == null) {
            canvas = Canvas.createIfSupported();
            canvas.setCoordinateSpaceHeight(600);
            canvas.setCoordinateSpaceWidth(600);
            canvas.setHeight(canvas.getCoordinateSpaceHeight() + "px");
            canvas.setWidth(canvas.getCoordinateSpaceWidth() + "px");
            initialize();
            addClickHandlers();
        }
        return canvas;
    }

    void initialize() {
        canvasRelativePoints.clear();
        clearCanvas();
        setupCrossSection();
        linesHaveBeenDrawn = false;
    }

    private void setupCrossSection() {
        getContext().beginPath();
        getContext().setStrokeStyle(CssColor.make("grey"));
        getContext().setLineWidth(2);
        getContext().moveTo(getCanvasWidth() / 2, 0);
        getContext().lineTo(getCanvasWidth() / 2, getCanvasHeight());
        getContext().moveTo(0, getCanvasHeight() / 2);
        getContext().lineTo(getCanvasWidth(), getCanvasHeight() / 2);
        getContext().setFillStyle(CssColor.make("red"));
        getContext().arc(getCanvasWidth() / 2, getCanvasHeight() / 2, 5, 0, Math.PI * 2.0, true);
        getContext().closePath();
        getContext().fill();
        getContext().stroke();
    }

    public void clearCanvas() {
        getContext().beginPath();
        getContext().setFillStyle(CssColor.make("#EBE8E9"));
        getContext().fillRect(0,0, getCanvasWidth(), getCanvasHeight());

    }

    private void addClickHandlers() {
       getCanvas().addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
               addPoint(event.getClientX() - getCanvas().getAbsoluteLeft(), event.getClientY() -  getCanvas().getAbsoluteTop());
           }
       });
    }

    private void addPoint(int x, int y) {
        if(linesHaveBeenDrawn) {
            Window.alert("You cannot a add a point once path has been generated");
            return;
        }
        if(canvasRelativePoints.size() > 9) {
            Window.alert("Max 9 point are allowed");
            return;
        }
        generateButton.setEnabled(true);
        canvasRelativePoints.add(new Point(x, y));
        drawClickPoint(x, y);
    }

    private void drawClickPoint(int x, int y) {
        getContext().beginPath();
        getContext().setStrokeStyle(CssColor.make("black"));
        getContext().setFillStyle(CssColor.make("black"));
        getContext().arc(x, y, 3, 0, Math.PI * 2.0, true);
        getContext().fill();
        getContext().closePath();
        getContext().stroke();
    }


    private Context2d getContext() {
        if(context == null ) {
            context = getCanvas().getContext2d();
        }
        return context;
    }


    private int getCanvasWidth() {
        return getCanvas().getCoordinateSpaceWidth();
    }

    private int getCanvasHeight() {
        return getCanvas().getCoordinateSpaceHeight();
    }


    List<Point> getGridRelativePoints() {
        List<Point> result = new ArrayList<Point>();
        for(Point point: canvasRelativePoints) {
            result.add(new Point(point.getX() - getCanvasWidth()/2, point.getY() - getCanvasHeight()/2));
        }
        return result;
    }

    void drawLines(List<Point> gridRelatedPoints) {
        linesHaveBeenDrawn = true;
        Point lastPoint = new Point(getCanvasWidth()/2, getCanvasHeight()/2);
        for(Point point: getCanvasRelativePoints(gridRelatedPoints)) {
            drawLine(lastPoint, point);
            lastPoint = point;
        }
    }

    private void drawLine(Point point1, Point point2) {
        getContext().beginPath();
        getContext().setStrokeStyle(CssColor.make("black"));
        getContext().setLineWidth(1);
        getContext().moveTo(point1.getX(), point1.getY());
        getContext().lineTo(point2.getX(), point2.getY());
        getContext().closePath();
        getContext().stroke();
    }

    private List<Point> getCanvasRelativePoints(List<Point> gridRelatedPoints) {
        List<Point> result = new ArrayList<Point>();
        for(Point point: gridRelatedPoints) {
            result.add(new Point(point.getX() + getCanvasWidth()/2, point.getY() + getCanvasHeight()/2));
        }
        return result;
    }

    boolean linesHaveBeenDrawn() {
        return linesHaveBeenDrawn;
    }
}
