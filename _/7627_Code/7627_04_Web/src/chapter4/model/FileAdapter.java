package chapter4.model;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public String getContentType() {
		String contentType = null;
		try {
			URLConnection connection = file.toURI().toURL().openConnection();
			contentType = connection.getContentType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentType;
	}

	public List<FileAdapter> getFiles() {
		List<FileAdapter> list = null;
		File[] files = file.listFiles();
		if (files != null && files.length > 0) {
			list = new ArrayList<FileAdapter>();
			for (File file : files) {
				list.add(new FileAdapter(file));
			}
		}
		return list;
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

	public String getType() {
		String type = "file";
		if (file.isDirectory()) {
			type = "directory";
		}
		return type;
	}

	public String getNodeType() {
		return file.isDirectory() ? "folder" : "document";
	}

	public String getUrl() {
		String url = null;
		try {
			url = file.toURI().toURL().toExternalForm();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public String toString() {
		return file.getName();
	};
}
