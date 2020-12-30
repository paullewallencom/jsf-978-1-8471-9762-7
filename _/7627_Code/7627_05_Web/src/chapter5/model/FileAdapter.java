package chapter5.model;

import java.net.URLConnection;
import java.util.Date;

/**
 * This class is a model adapter for the java.io.File class. It makes it easier
 * to render the file's information in the view.
 * 
 * @author Ian
 * 
 */
public class FileAdapter {

	private java.io.File file;

	public FileAdapter(java.io.File file) {
		this.file = file;
	}

	public Date getLastModified() {
		return new Date(file.lastModified());
	}

	public String getName() {
		return file.getName();
	}

	public long getSize() {
		return file.length();
	}

	public String getUrl() {
		String url = null;
		try {
			url = file.toURL().toExternalForm();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public String getType() {
		String type = "file";
		if (file.isDirectory()) {
			type = "directory";
		}
		return type;
	}

	public String getContentType() {
		String contentType = null;
		try {
			URLConnection connection = file.toURL().openConnection();
			contentType = connection.getContentType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentType;
	}

	public String toString() {
		return file.getName();
	};
}
