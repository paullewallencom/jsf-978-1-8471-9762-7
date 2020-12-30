package chapter5.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import chapter5.model.FileAdapter;
import chapter5.model.InputFileData;
import chapter5.validator.CustomDateValidator;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.component.tree.IceUserObject;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

public class BackingBean {

	public static final long KILOBYTE_LENGTH_BYTES = 1024l;

	public static final long MEGABYTE_LENGTH_BYTES = 1048000l;

	private InputFileData currentFile;

	private Date date;

	private final List<InputFileData> fileList = Collections.synchronizedList(new ArrayList<InputFileData>());

	private int fileProgress;

	private String name;

	private Integer number;

	private String pattern = "date";

	private Boolean rememberMe;

	private DefaultTreeModel treeModel;

	private DefaultTreeModel treeModel2;

	private Effect valueChangeEffect;

	private String word;

	private List<String> words = new ArrayList<String>();

	public BackingBean() {
		valueChangeEffect = new Highlight("#fda505");
	}

	public void addWord(ActionEvent event) {
		if (word != null && word.length() > 0) {
			words.add(word);
		}
	}

	private void buildTreeModel(File file, DefaultMutableTreeNode node) {
		IceUserObject object = new IceUserObject(node);
		object.setText(file.getName());
		object.setLeaf(!file.isDirectory());
		node.setAllowsChildren(file.isDirectory());
		node.setUserObject(object);
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode();
				node.add(child);
				buildTreeModel(f, child);
			}
		}
	}

	private void buildTreeModelWithIcons(File file, DefaultMutableTreeNode node) {
		IceUserObject object = new IceUserObject(node);
		object.setText(file.getName());
		object.setLeaf(!file.isDirectory());
		object.setLeafIcon("document.png");
		object.setBranchContractedIcon("yellow-folder-closed.png");
		object.setBranchExpandedIcon("yellow-folder-open.png");
		node.setAllowsChildren(file.isDirectory());
		node.setUserObject(object);
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode();
				node.add(child);
				buildTreeModelWithIcons(f, child);
			}
		}
	}

	public void effectChangeListener(ValueChangeEvent event) {
		valueChangeEffect.setFired(false);
	}

	public void fileUploadProgress(EventObject event) {
		InputFile ifile = (InputFile) event.getSource();
		fileProgress = ifile.getFileInfo().getPercent();
	}

	public List<FileAdapter> getAdaptedUploadedFiles() {
		List<FileAdapter> list = null;
		List<File> files = getUploadedFiles();
		if (files != null && !files.isEmpty()) {
			list = new ArrayList<FileAdapter>();
			for (File file : files) {
				list.add(new FileAdapter(file));
			}
		}
		return list;
	}

	public InputFileData getCurrentFile() {
		return currentFile;
	}

	public Date getDate() {
		return date;
	}

	public List<InputFileData> getFileList() {
		return fileList;
	}

	public int getFileProgress() {
		return fileProgress;
	}

	public String getName() {
		valueChangeEffect.setFired(false);
		return name;
	}

	public Integer getNumber() {
		return number;
	}

	public String getPattern() {
		return pattern;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public Date getToday() {
		return new Date();
	}

	public DefaultTreeModel getTreeModel() {
		if (treeModel == null) {
			File dir = getUploadDirectory();
			DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
			buildTreeModel(dir, rootTreeNode);
			((IceUserObject) rootTreeNode.getUserObject()).setExpanded(true);
			treeModel = new DefaultTreeModel(rootTreeNode);
		}
		return treeModel;
	}

	public DefaultTreeModel getTreeModel2() {
		if (treeModel2 == null) {
			File dir = getUploadDirectory();
			DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
			buildTreeModelWithIcons(dir, rootTreeNode);
			((IceUserObject) rootTreeNode.getUserObject()).setExpanded(true);
			treeModel2 = new DefaultTreeModel(rootTreeNode);
		}
		return treeModel2;
	}

	public File getUploadDirectory() {
		String path = System.getProperty("java.io.tmpdir");
		String separator = File.separator;
		if (!path.endsWith(separator)) {
			path += separator;
		}
		File dir = new File(path, "upload-demo");
		return dir;
	}

	public List<File> getUploadedFiles() {
		List<File> list = null;
		File dir = getUploadDirectory();
		if (dir.exists()) {
			list = new ArrayList<File>(Arrays.asList(dir.listFiles()));
			Collections.sort(list);
		}
		return list;
	}

	public Effect getValueChangeEffect() {
		return valueChangeEffect;
	}

	public String getWord() {
		return word;
	}

	public List<String> getWords() {
		return words;
	}

	public void removeWord(ActionEvent event) {
		words.remove(word);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	public void uploadFile(ActionEvent event) {
		InputFile inputFile = (InputFile) event.getSource();
		FileInfo fileInfo = inputFile.getFileInfo();
		if (fileInfo.getStatus() == FileInfo.SAVED) {
			System.out.println("Saved file: " + fileInfo.getFile().getPath());
			// reference our newly updated file for display purposes and
			// added it to our history file list.
			currentFile = new InputFileData(fileInfo);
			synchronized (fileList) {
				fileList.add(currentFile);
			}
		}
	}

	public void validateBirthDate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		new CustomDateValidator().validate(context, component, object);
	}

}
