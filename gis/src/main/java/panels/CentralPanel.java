package panels;

import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.event.OMMouseMode;
import com.bbn.openmap.gui.BasicMapPanel;
import com.bbn.openmap.gui.OverlayMapPanel;
import com.bbn.openmap.gui.ToolPanel;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMPoint;
import com.bbn.openmap.proj.coords.LatLonPoint;
import util.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Properties;

public class CentralPanel extends JPanel {

    public CentralPanel(){
        BasicMapPanel mapPanel = new OverlayMapPanel();

        ShapeLayer shapeLayer = new ShapeLayer();
        OMGraphicHandlerLayer layer = new OMGraphicHandlerLayer();

        OMPoint point = new OMPoint(53.09, 107.24);
        point.setFillPaint(Color.darkGray);
        OMGraphicList graphics = new OMGraphicList();
        graphics.add(point);
        layer.setList(graphics);

        Properties shapeLayerProps = new Properties();
        //shapeLayerProps.put("prettyName", "Political Solid");
        shapeLayerProps.put("lineColor", "000000");
        //shapeLayerProps.put("fillColor", "BDDE83");
        shapeLayerProps.put("shapeFile", "src/main/resources/map/island.shp");
        //shapeLayerProps.put("spatialIndex", "data/500m-join.ssx");
        shapeLayer.setProperties(shapeLayerProps);



        MapBean mapBean = mapPanel.getMapBean();
        mapBean.setCenter(new LatLonPoint.Double(53.09, 107.24));

        mapPanel.getMapBean().add(shapeLayer);


        mapBean.setScale(1200000f);


        mapPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        mapPanel.addMapComponent(new LayerHandler());
        // Add MouseDelegator, which handles mouse modes (managing mouse
        // events via MouseModes)
        //mapPanel.addMapComponent(new MouseDelegator());
        // Add OMMouseMode, which handles how the map reacts to mouse
        // movements
        mapPanel.addMapComponent(new OMMouseMode());
        // Add a ToolPanel for widgets on the north side of the map.
        mapPanel.addMapComponent(new ToolPanel());
        // Add a LayersPanel, which lets you control layers
        //mapPanel.addMapComponent(new LayersPanel());


        this.add(mapPanel);
    }


}
