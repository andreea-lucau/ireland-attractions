package com.andreea.ireland.client;

import com.andreea.ireland.shared.Poi;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("pois")
public interface PoiService extends RemoteService {
	Poi[] getPois(String type);
}