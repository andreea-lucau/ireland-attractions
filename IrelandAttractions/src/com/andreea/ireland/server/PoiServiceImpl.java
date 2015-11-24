package com.andreea.ireland.server;

import java.io.File;
import java.io.FileFilter;
import java.util.logging.Logger;

import com.andreea.ireland.client.PoiService;
import com.andreea.ireland.shared.Poi;
import com.andreea.ireland.shared.PoiFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The server-side implementation of the RPC service.
 */
public class PoiServiceImpl extends RemoteServiceServlet implements PoiService {

	private static final long serialVersionUID = 1L;

	private final String POIS_DIR = "pois";
    
    private static final Logger LOGGER = Logger.getLogger(
    		PoiServiceImpl.class.getName());

    public Poi[] getPois(String poi_type) throws IllegalArgumentException {
        String[] poiFiles = getPoiFiles(poi_type);

        Poi[] pois = new Poi[poiFiles.length];
        for (int i = 0; i < poiFiles.length; i++) {
            pois[i] = PoiFactory.newInstance(poiFiles[i]);
        }
        
        LOGGER.info("Found " + pois.length + " pois of type " + poi_type);

        return pois;
    }

    private String[] getPoiFiles(String poi_type) {
    	File dir = new File(this.POIS_DIR + "/" + poi_type);
    	FileFilter fileFilter = new XMLFileFilter();
    	
    	File[] files = dir.listFiles(fileFilter);
    	String[] poiFiles = new String[files.length];
    	for (int i = 0; i < files.length; i++) {
    	   poiFiles[i] = files[i].getPath();
    	}
    	
    	LOGGER.info("Found " + poiFiles.length + " files for pois of type " + poi_type);

        return poiFiles;
    }
    
    private class XMLFileFilter implements FileFilter
    {
      private final String[] okFileExtensions = 
        new String[] {"xml"};
     
      public boolean accept(File file)
      {
        for (String extension : okFileExtensions)
        {
          if (file.getName().toLowerCase().endsWith(extension))
          {
            return true;
          }
        }
        return false;
      }
    }
}