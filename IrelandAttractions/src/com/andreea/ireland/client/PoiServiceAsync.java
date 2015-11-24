package com.andreea.ireland.client;

import com.andreea.ireland.shared.Poi;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>PoiService</code>.
 */
public interface PoiServiceAsync {
	void getPois(String type, AsyncCallback<Poi[]> callback)
			throws IllegalArgumentException;
}