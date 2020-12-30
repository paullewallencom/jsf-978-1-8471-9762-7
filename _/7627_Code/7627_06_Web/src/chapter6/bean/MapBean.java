package chapter6.bean;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import chapter6.model.Location;

@Name("mapBean")
@Scope(ScopeType.CONVERSATION)
public class MapBean {

	private List<Location> locations;

	private Location selectedLocation;

	private int zoom = 15;

	public Location findLocation(String id) {
		Location found = null;
		for (Location location : getLocations()) {
			if (location.getId().equals(id)) {
				found = location;
				break;
			}
		}
		return found;
	}

	public List<Location> getLocations() {
		if (locations == null) {
			locations = new ArrayList<Location>();
			locations.add(new Location("Eiffel Tower (Paris)", 48.858333d, 2.294444, zoom));
			locations.add(new Location("Parthenon (Greece)", 37.971389d, 23.726389, zoom));
			locations.add(new Location("Colosseum (Rome)", 41.89d, 12.49222d, zoom));
		}
		return locations;
	}

	public Location getSelectedLocation() {
		return selectedLocation;
	}

	public int getZoom() {
		return zoom;
	}

	public void setSelectedLocation(Location location) {
		this.selectedLocation = location;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

}
