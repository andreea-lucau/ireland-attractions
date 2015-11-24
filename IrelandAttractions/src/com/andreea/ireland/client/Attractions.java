package com.andreea.ireland.client;

import com.andreea.ireland.client.resources.HTMLResources;
import com.andreea.ireland.shared.Poi;

import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class Attractions extends Composite {
	private static final Logger LOGGER = Logger.getLogger(
    		Attractions.class.getName());
	
	private static final String IRELAND_MAP_IMG = "images/ireland_map.jpg";
	
	private static final String POI_MARKER_IMG = "http://maps.google.com/mapfiles/ms/icons/green-dot.png";
	private static final String ACTIVE_POI_MARKER_IMG = "http://maps.google.com/mapfiles/ms/icons/red-dot.png";
	
    private static AttractionsUiBinder uiBinder = GWT.create(AttractionsUiBinder.class);

    private Vector<Marker> markers;
    private Vector<Poi> pois;

    private LatLng mapCentre;
    private int mapZoom;

    interface AttractionsUiBinder extends UiBinder<Widget, Attractions> {}

    public Attractions() {
        initWidget(uiBinder.createAndBindUi(this));

        markers = new Vector<Marker>();
        pois = new Vector<Poi>();
    	
        home.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                clearNavbar();
                displayHomeInfo();
                }
                });
    }

    public void displayHomeInfo() {
        clearNavbar();
        infoCard.clear();
        
        HTMLPanel description = new HTMLPanel(HTMLResources.INSTANCE.getHomepageHTML().getText());
        infoCard.add(description);

        setCanvasImage(Attractions.IRELAND_MAP_IMG, "50%");
    }

    private void setCanvasImage(String imageUrl, String width) {
        Image image = new Image();
        image.setUrl(imageUrl);
        image.setWidth(width);

        canvas.setWidget(image);
    }

    public void activateNavLink(NavLink nv) {
        clearNavbar();
        nv.addStyleName("active");
        
        LOGGER.info("Activating " + nv.getName());

        if (nv == irelandAttractions) {
            mapCentre = LatLng.newInstance(53.41291, -8.243889999999965);
            mapZoom = 6;
        } else {
            mapCentre = LatLng.newInstance(53.3498053, -6.260309699999993);
            mapZoom = 13;
        }
    }

    public void clearNavbar() {
        irelandAttractions.removeStyleName("active");
        dublinAttractions.removeStyleName("active");
        bestPlacesToEat.removeStyleName("active");
        bestPlacesToHaveFun.removeStyleName("active");
    }

    public void displayPois(Poi[] pois) {
        this.markers.clear();
        this.pois.clear();

        displayMap(pois);
        updatePoiInfo(markers.get(0));
    }

    public void updatePoiInfo(Marker selectedMarker) {
        for (Marker marker: markers) {
            if (marker == selectedMarker)
                marker.setImage(Attractions.ACTIVE_POI_MARKER_IMG);
            else
                marker.setImage(Attractions.POI_MARKER_IMG);

            marker.getIcon().setIconSize(Size.newInstance(30, 30));
        }

        displayPoiInfo(markers.indexOf(selectedMarker));
    }

    private void displayPoiInfo(int poiIndex) {
        infoCard.clear();
        Poi poi = pois.get(poiIndex);

        Heading title = new Heading(2, poi.getTitle());
        infoCard.add(title);
        
        for (Map.Entry<String, String> detail : poi.getDetails().entrySet()) {
        	Paragraph info = new Paragraph("<b>" + detail.getKey() +
        			": </b>" + detail.getValue());
        	
        	infoCard.add(info);
        }
        
        HTMLPanel description = new HTMLPanel(poi.getDescription());
        infoCard.add(description);
    }

    private void displayMap(Poi[] pois) {
        MapWidget map = new MapWidget(mapCentre, mapZoom);
        map.setSize("100%", "100%");
        map.addControl(new LargeMapControl());

        // Add POI markers
        for (int i = 0; i < pois.length; i++) {
            this.pois.add(pois[i]);

            Marker marker = new Marker(LatLng.newInstance(pois[i].getLatitude(), pois[i].getLongitude()));
            marker.setImage(Attractions.POI_MARKER_IMG);
            marker.getIcon().setIconSize(Size.newInstance(30, 30));

            this.markers.add(marker);
            
            markers.get(i).addMarkerClickHandler(new MarkerClickHandler() {
                    @Override
                    public void onClick(MarkerClickEvent event) {
                    updatePoiInfo((Marker)event.getSource());
                    }
                    });

            map.addOverlay(markers.get(i));
        }

        canvas.clear();
        canvas.add(map);
    }

    @UiField
        HTMLPanel infoCard;

    @UiField
        SimplePanel canvas;

    @UiField
        Brand home;

    @UiField
        NavLink irelandAttractions;

    @UiField
        NavLink dublinAttractions;

    @UiField
        NavLink bestPlacesToEat;

    @UiField
        NavLink bestPlacesToHaveFun;
}