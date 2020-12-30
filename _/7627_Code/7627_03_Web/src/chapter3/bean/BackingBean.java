package chapter3.bean;

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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.myfaces.custom.navmenu.NavigationMenuItem;
import org.apache.myfaces.custom.tree.DefaultMutableTreeNode;
import org.apache.myfaces.custom.tree.model.DefaultTreeModel;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;

import chapter3.model.FileAdapter;
import chapter3.validator.CustomDateValidator;

public class BackingBean extends AbstractBean {

	private Date date;

	private String name;

	private Integer number;

	private ProductBean productBean;

	private Boolean rememberMe;

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

	public Date getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public List<NavigationMenuItem> getNavigationMenuItems() {
		String url = getBaseURL();
		List<NavigationMenuItem> list = new ArrayList<NavigationMenuItem>();
		NavigationMenuItem item1 = new NavigationMenuItem("Home", url + "/index.jsf");
		NavigationMenuItem item2 = new NavigationMenuItem("About", url + "/about.jsf");
		NavigationMenuItem item3 = productBean.getProductsNavigationMenuItemPush();
		NavigationMenuItem item4 = new NavigationMenuItem("Services", url + "/services.jsf");
		NavigationMenuItem item5 = new NavigationMenuItem("Contact Us", url + "/contact.jsf");
		list.add(item1);
		list.add(item2);
		list.add(item3);
		list.add(item4);
		list.add(item5);
		return list;
	}

	public Integer getNumber() {
		return number;
	}

	public Boolean getRememberMe() {
		return rememberMe;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
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

	public void validateBirthDate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		new CustomDateValidator().validate(context, component, object);
	}

}
