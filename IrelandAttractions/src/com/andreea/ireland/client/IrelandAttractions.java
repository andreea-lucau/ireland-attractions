package com.andreea.ireland.client;

import com.andreea.ireland.shared.Poi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;


public class IrelandAttractions implements EntryPoint {
    private final PoiServiceAsync poiService = GWT.create(PoiService.class);

    public void onModuleLoad() {
        Maps.loadMapsApi("", "2", false, new Runnable() {
                public void run() {
                buildUI();
                }
        });
    }

    private void buildUI() {
        final Attractions attractions = new Attractions();
        attractions.displayHomeInfo();

        RootPanel.get().add(attractions);
        loadAttractionsCallbacks(attractions);
    }

    private void loadAttractionsCallbacks(final Attractions w) {
        final AsyncCallback<Poi[]> poisCallback = getPoisCallback();

        w.irelandAttractions.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                w.activateNavLink(w.irelandAttractions);
                poiService.getPois("ireland-attractions", poisCallback);
                }
                });

        w.dublinAttractions.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                w.activateNavLink(w.dublinAttractions);
                poiService.getPois("dublin-attractions", poisCallback);
                }
                });

        w.bestPlacesToEat.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                w.activateNavLink(w.bestPlacesToEat);
                poiService.getPois("best-places-to-eat", poisCallback);
                }
                });

        w.bestPlacesToHaveFun.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                w.activateNavLink(w.bestPlacesToHaveFun);
                poiService.getPois("best-places-to-have-fun", poisCallback);
                }
                });
    }

    private AsyncCallback<Poi[]> getPoisCallback() {
        AsyncCallback<Poi[]> callback = new AsyncCallback<Poi[]>() {
            public void onSuccess(Poi[] result) {
                displayPois(result);
            }

            public void onFailure(Throwable caught) {
                displayPoisError();
            }
        };

        return callback;
    }

    public void displayPois(Poi[] pois) {
        Attractions attractions = (Attractions)RootPanel.get().getWidget(0);
        attractions.displayPois(pois);
    }

    public void displayPoisError() {}	
}