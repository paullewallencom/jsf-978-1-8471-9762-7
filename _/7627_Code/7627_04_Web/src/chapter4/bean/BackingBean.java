package chapter4.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.myfaces.custom.navmenu.NavigationMenuItem;
import org.apache.myfaces.custom.tree.DefaultMutableTreeNode;
import org.apache.myfaces.custom.tree.model.DefaultTreeModel;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;
import org.apache.myfaces.trinidad.event.PollEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyMenuModel;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.DefaultBoundedRangeModel;
import org.apache.myfaces.trinidad.model.ProcessMenuModel;

import chapter4.model.FileAdapter;
import chapter4.model.NavigationItem;
import chapter4.validator.CustomDateValidator;

public class BackingBean extends AbstractBean {

	private Date date;

	private String message;

	private String name;

	private Integer number = 0;

	private ProcessMenuModel processMenuModel;

	private ProductBean productBean;

	private DefaultBoundedRangeModel progressModel;

	private Boolean rememberMe;

	private ProcessMenuModel trainModel;

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

	public String getMessage() {
		return message;
	}

	public Date getMinimumDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.YEAR, -120);
		Date date = calendar.getTime();
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

	public ProcessMenuModel getProcessMenuModel() {
		if (processMenuModel == null) {
			processMenuModel = new ProcessMenuModel();
			List<NavigationItem> list = new ArrayList<NavigationItem>();
			list.add(new NavigationItem("Step 1", "/processChoiceBar01_1.jsf", "processChoiceBar01"));
			list.add(new NavigationItem("Step 2", "/processChoiceBar01_2.jsf", "processChoiceBar02"));
			list.add(new NavigationItem("Step 3", "/processChoiceBar01_3.jsf", "processChoiceBar03"));
			list.add(new NavigationItem("Step 4", "/processChoiceBar01_4.jsf", "processChoiceBar04"));
			list.add(new NavigationItem("Step 5", "/processChoiceBar01_5.jsf", "processChoiceBar05"));
			processMenuModel.setViewIdProperty("viewId");
			processMenuModel.setWrappedData(list);
		}
		return processMenuModel;
	}

	public DefaultBoundedRangeModel getProgressModel() {
		if (progressModel == null) {
			progressModel = new DefaultBoundedRangeModel();
			progressModel.setMaximum(10);
		}
		return progressModel;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public Date getToday() {
		Date date = new Date();
		System.out.println("Today = " + date);
		return date;
	}

	public ProcessMenuModel getTrainModel() {
		if (trainModel == null) {
			trainModel = new ProcessMenuModel();
			List<NavigationItem> list = new ArrayList<NavigationItem>();
			list.add(new NavigationItem("Step 1", "/train01_1.jsf", "train_1"));
			list.add(new NavigationItem("Step 2", "/train01_2.jsf", "train_2"));
			list.add(new NavigationItem("Step 3", "/train01_3.jsf", "train_3"));
			list.add(new NavigationItem("Step 4", "/train01_4.jsf", "train_4"));
			list.add(new NavigationItem("Step 5", "/train01_5.jsf", "train_5"));
			trainModel.setViewIdProperty("viewId");
			trainModel.setWrappedData(list);
		}
		return trainModel;
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

	public org.apache.myfaces.trinidad.model.MenuModel getUploadedFilesMenuModel() {
		org.apache.myfaces.trinidad.model.MenuModel model = null;
		File dir = getUploadDirectory();
		FileAdapter adapter = new FileAdapter(dir);
		model = new ChildPropertyMenuModel(adapter, "files", null);
		return model;
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

	public org.apache.myfaces.trinidad.model.TreeModel getUploadedFilesTrinidadTreeModel() {
		org.apache.myfaces.trinidad.model.TreeModel model = null;
		File dir = getUploadDirectory();
		FileAdapter adapter = new FileAdapter(dir);
		model = new ChildPropertyTreeModel(adapter, "files");
		return model;
	}

	public String getWord() {
		return word;
	}

	public List<String> getWords() {
		return words;
	}

	public boolean isAdministrator() {
		boolean admin = false;
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		admin = request.isUserInRole("Administrator");
		return admin;
	}

	public String nextStep() {
		return "next";
	}

	public void pollListener(PollEvent event) {
		System.out.println("Event received: " + event);
		number++;
		if (number < 5) {
			message = "Processing...";
		} else if (number == 5) {
			message = "Processing complete.";
		} else {
			message = "Processing...";
			number = 1;
		}
	}

	public String previousStep() {
		return "previous";
	}

	public void progressListener(PollEvent event) {
		DefaultBoundedRangeModel model = getProgressModel();
		long value = model.getValue();
		if (value == model.getMaximum()) {
			value = 0;
		} else {
			value++;
		}
		model.setValue(value);
	}

	public void removeWord(ActionEvent event) {
		words.remove(word);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public void slowPollListener(PollEvent event) {
		try {
			Thread.sleep(3000);
			number++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
