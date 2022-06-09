package panels;

import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.MouseDelegator;
import com.bbn.openmap.event.OMMouseMode;
import com.bbn.openmap.gui.BasicMapPanel;
import com.bbn.openmap.gui.OverlayMapPanel;
import com.bbn.openmap.gui.ToolPanel;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMPoint;
import com.bbn.openmap.proj.coords.LatLonPoint;
import models.Place;
import models.Sample;
import util.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CentralPanel extends JPanel {

    List<Sample> samples;
    BasicMapPanel mapPanel;
    OMGraphicHandlerLayer graphicHandlerLayer;

    public CentralPanel(){
        setLayout(new BorderLayout());

        mapPanel = new BasicMapPanel();

        ShapeLayer shapeLayer = new ShapeLayer();

        Properties shapeLayerProps = new Properties();
        shapeLayerProps.put("prettyName", "Olkhon Solid");
        shapeLayerProps.put("lineColor", "000000");
        shapeLayerProps.put("shapeFile", "src/main/resources/map/island.shp");
        shapeLayer.setProperties(shapeLayerProps);

        MapBean mapBean = mapPanel.getMapBean();
        mapBean.setCenter(new LatLonPoint.Double(53.09, 107.24));

        mapPanel.getMapBean().add(shapeLayer);

        mapBean.setScale(300000f);

        graphicHandlerLayer = new OMGraphicHandlerLayer();

        mapPanel.addMapComponent(new LayerHandler());
        mapPanel.addMapComponent(new OMMouseMode());
        mapPanel.addMapComponent(new ToolPanel());

        mapPanel.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Controller.getInstance().updateCurrentSample(findNearestSample(mapPanel.getMapBean().getCoordinates(e).getY(), mapPanel.getMapBean().getCoordinates(e).getX()));
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

        add(mapPanel);
    }

    public void update(){
        mapPanel.getMapBean().remove(graphicHandlerLayer);
        graphicHandlerLayer = new OMGraphicHandlerLayer();

        OMPoint point;
        OMGraphicList graphics = new OMGraphicList();

        for (int i = 0; i < samples.size(); i++){
            point = new OMPoint(samples.get(i).getLat(), samples.get(i).get_long());
            point.setFillPaint(Color.BLUE);
            graphics.add(point);
        }

        graphicHandlerLayer.setList(graphics);
        mapPanel.getMapBean().add(graphicHandlerLayer);
        mapPanel.revalidate();
        mapPanel.repaint();

    }

    private Sample findNearestSample(double lat, double _long){
        if (samples == null || samples.size()==0){
            return null;
        }
        double minDistance = 10000;
        double distance;
        int index = 0;
        for (int i = 0; i < samples.size(); i++){
            distance = samples.get(i).getDistanceTo(lat, _long);
            if (minDistance > distance){
                minDistance = distance;
                index = i;
                }
            }
        return samples.get(index);
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}
