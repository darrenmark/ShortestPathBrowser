package com.darren.sp.browser.client;

import com.darren.sp.browser.shared.Point;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

import java.util.List;

/**
 */
public class ShortestPathView implements IsWidget{
    private CanvasMap canvas;
    private VerticalLayoutContainer layoutContainer;
    private Viewport viewport;
    private ContentPanel mainPanel;
    private ContentPanel panel;
    private TextButton drawPathButton;
    private TextButton clearButton;
    private AutoProgressMessageBox progressMessageBox;
    private ShortestPathServiceAsync async = GWT.create(ShortestPathService.class);


    public Widget asWidget() {
        if(!Canvas.isSupported()) {
            return new Label("Please update your browser as Canvas is not supported.");
        }
        return getLayoutContainer();
    }



    private CanvasMap getCanvas() {
        if(canvas == null) {
            canvas = new CanvasMap(getCalculatePathButton());
        }
        return canvas;
    }

    private VerticalLayoutContainer getLayoutContainer() {
        if(layoutContainer == null) {
            layoutContainer = new VerticalLayoutContainer();
            layoutContainer.add(getButtonWidget());
            layoutContainer.add(getCanvas());
        }
        return layoutContainer;
    }

    private ContentPanel getMainPanel() {
        if(mainPanel == null) {
            mainPanel = new ContentPanel();
            mainPanel.setHeaderVisible(false);
            mainPanel.setBodyStyle("backgroundColor:white");
        }
        return mainPanel;
    }

    private IsWidget getButtonWidget() {
        if(panel == null) {
            panel = new ContentPanel();
            panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.START);
            panel.setStyleName("");
            panel.setHeaderVisible(false);
            panel.addButton(getCalculatePathButton());
            panel.addButton(getClearButton());
       }
        return panel;
    }

    private TextButton getCalculatePathButton() {
        if(drawPathButton == null) {
            drawPathButton = new TextButton("Draw Path");
            drawPathButton.addSelectHandler(getDrawPathSelectHandler());
            drawPathButton.setEnabled(false);
        }
        return drawPathButton;
    }

    private TextButton getClearButton() {
        if(clearButton == null) {
            clearButton = new TextButton("Clear");
            clearButton.addSelectHandler(getClearSelectHandler());
        }
        return clearButton;
    }


    private SelectEvent.SelectHandler getDrawPathSelectHandler() {
        return new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent selectEvent) {
               getProgressMessageBox().show();
                async.calculateShortestPath(new Point(0,0), canvas.getGridRelativePoints(), new AsyncCallback<List<Point>>() {
                    public void onFailure(Throwable caught) {
                        getProgressMessageBox().hide();
                        Window.alert(caught.getMessage());
                    }
                    public void onSuccess(List<Point> result) {
                        getProgressMessageBox().hide();
                        canvas.drawLines(result);
                        getCalculatePathButton().setEnabled(false);
                    }
                });
            }
        };
    }

    private SelectEvent.SelectHandler getClearSelectHandler() {
        return new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent selectEvent) {
                canvas.initialize();
                getCalculatePathButton().setEnabled(false);
            }
        };
    }

    private AutoProgressMessageBox getProgressMessageBox() {
        if(progressMessageBox == null) {
            progressMessageBox = new AutoProgressMessageBox("Progress", "Processing your data, please wait...");
            progressMessageBox.setProgressText("Calculation Path...");
            progressMessageBox.auto();
        }
        return progressMessageBox;
    }


}
