package chapter6.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Simple POJO class for RichFaces JSON data transfer.
 * 
 * @author Ian
 * 
 */
public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();

	private double latitude;

	private double longitude;

	private String name;

	private int zoom;

	public Location() {
	}

	public Location(String name, double latitude, double longitude, int zoom) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.zoom = zoom;
	}

	public String getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	public int getZoom() {
		return zoom;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

}
