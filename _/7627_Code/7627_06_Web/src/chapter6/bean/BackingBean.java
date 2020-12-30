package chapter6.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.myfaces.custom.tree.DefaultMutableTreeNode;
import org.apache.myfaces.custom.tree.model.DefaultTreeModel;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import chapter6.model.FileAdapter;
import chapter6.model.Product;

@Name("backingBean")
@Scope(ScopeType.CONVERSATION)
public class BackingBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date date;

	private Float floatValue;

	private String name;

	private boolean pollEnabled;

	private int pollCount;

	public boolean isPollEnabled() {
		pollEnabled = pollCount < 5;
		return pollEnabled;
	}

	public void setPollEnabled(boolean pollEnabled) {
		this.pollEnabled = pollEnabled;
	}

	public int getPollCount() {
		return pollCount++;
	}

	public void setPollCount(int pollCount) {
		this.pollCount = pollCount;
	}

	private String name1;

	private String name2;

	private Integer number;

	private Boolean rememberMe;

	private Integer result1;

	private Integer result2;

	private UploadedFile uploadedFile;

	private String word;

	private List<String> words = new ArrayList<String>();

	public void addWord(ActionEvent event) {
		if (word != null && word.length() > 0) {
			words.add(word);
		}
	}

	@SuppressWarnings("unchecked")
	private void buildTree2Model(File dir, TreeNode root) {
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				boolean leaf = false;
				String name = file.getName();
				String type = null;
				if (file.isDirectory()) {
					type = "folder";
				} else {
					type = "file";
					leaf = true;
				}
				TreeNode node = new TreeNodeBase(type, name, leaf);
				root.getChildren().add(node);
				if (file.isDirectory()) {
					buildTree2Model(file, node);
				}
			}
		}
	}

	private void buildTreeModel(File dir, DefaultMutableTreeNode root) {
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName());
				node.setUserObject(new FileAdapter(file));
				root.insert(node);
				if (file.isDirectory()) {
					buildTreeModel(file, node);
				}
			}
		}
	}

	public void calculate1(ActionEvent event) {
		try {
			Thread.sleep(3000);
			Random random = new Random();
			result1 = random.nextInt() * random.nextInt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void calculate2(ActionEvent event) {
		try {
			Thread.sleep(3000);
			Random random = new Random();
			result2 = random.nextInt() * random.nextInt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String string = null;
		if (value instanceof String) {
			string = (String) value;
		} else if (value instanceof Product) {
			string = ((Product) value).getId();
		}
		return string;
	}

	public Date getDate() {
		return date;
	}

	public Float getFloatValue() {
		return floatValue;
	}

	public String getName() {
		return name;
	}

	public String getName1() {
		return name1;
	}

	public String getName2() {
		return name2;
	}

	public Integer getNumber() {
		return number;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public Integer getResult1() {
		return result1;
	}

	public Integer getResult2() {
		return result2;
	}

	public Date getToday() {
		return new Date();
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

	public UploadedFile getUploadedFile() {
		return uploadedFile;
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

	public TreeNode getUploadedFilesTree2Model() {
		File dir = getUploadDirectory();
		TreeNode root = new TreeNodeBase("folder", dir.getName(), false);
		buildTree2Model(dir, root);
		return root;
	}

	public org.apache.myfaces.custom.tree.model.TreeModel getUploadedFilesTreeModel() {
		File dir = getUploadDirectory();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(dir.getName());
		root.setUserObject(new FileAdapter(dir));
		buildTreeModel(dir, root);
		org.apache.myfaces.custom.tree.model.TreeModel model = new DefaultTreeModel(root);
		return model;
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

	public void setFloatValue(Float floatValue) {
		this.floatValue = floatValue;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public void setResult1(Integer result1) {
		this.result1 = result1;
	}

	public void setResult2(Integer result2) {
		this.result2 = result2;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	public String submitOrder() {
		// TODO Submit order for processing
		// order = new Order();
		return "success";
	}

	public void uploadFile(ActionEvent event) {
		InputStream in = null;
		OutputStream out = null;
		try {
			if (uploadedFile != null) {
				in = uploadedFile.getInputStream();
				File dir = getUploadDirectory();
				if (!dir.exists()) {
					if (!dir.mkdir()) {
						throw new IOException("Unable to make directory: " + dir);
					}
				}
				File file = new File(dir, uploadedFile.getName());
				out = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				while (in.read(buffer) != -1) {
					out.write(buffer);
				}
				out.flush();
				out.close();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
			}
		}
	}

}
