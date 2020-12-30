package chapter5.model;

import chapter5.bean.BackingBean;

import com.icesoft.faces.component.inputfile.FileInfo;

import java.io.File;

public class InputFileData {

	private FileInfo fileInfo;

	private File file;

	public InputFileData(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
		this.file = fileInfo.getFile();
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Method to return the file size as a formatted string For example, 4000
	 * bytes would be returned as 4kb
	 * 
	 *@return formatted file size
	 */
	public String getSizeFormatted() {
		long ourLength = file.length();

		// Generate formatted label, such as 4kb, instead of just a plain number
		if (ourLength >= BackingBean.MEGABYTE_LENGTH_BYTES) {
			return ourLength / BackingBean.MEGABYTE_LENGTH_BYTES + " MB";
		} else if (ourLength >= BackingBean.KILOBYTE_LENGTH_BYTES) {
			return ourLength / BackingBean.KILOBYTE_LENGTH_BYTES + " KB";
		} else if (ourLength == 0) {
			return "0";
		} else if (ourLength < BackingBean.KILOBYTE_LENGTH_BYTES) {
			return ourLength + " B";
		}

		return Long.toString(ourLength);
	}
}
