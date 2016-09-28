
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 *
 * @author jack
 */
public class photoViewer extends JFrame implements ActionListener, Serializable, Accessible {

    Container mainWindow;
    JPanel controlPane;
    JMenuBar topBar = null;
    JMenu fileMenu = null;
    JMenuItem saveMenuItem, exitMenuItem, browseMenuItem, maintainMenuItem;
    JMenu viewMenu = null;
    JTextField currPageText;
    JTextArea descriptionTextArea, totalPageText;
    JLabel imageLabel = null;
    JPanel buttonPane;
    JTextField dateTextField;
    ArrayList<ProjPhoto> images;
    JButton nextButton, prevButton, deleteButton, saveButton, addButton;

    private static final long serialVersionUID = 1123L;

    int imageNumber = 0;

    public photoViewer(String title) // Constructor
    {

	// Set title to string passed in
	super(title);
	mainWindow = getContentPane();

	images = new ArrayList<ProjPhoto>();

	openLibrary();
	// importPhotos();
	// Create button to hold image
	// (TODO: Bound the scroll pane on init)
	imageLabel = new JLabel("", SwingConstants.CENTER);
	JScrollPane scrollPane = new JScrollPane(imageLabel);

	// This is temporary (TODO: INIT TO IMAGE 1)
	/*
	images.add(new ProjPhoto(new ImageIcon("1.jpg")));
	images.add(new ProjPhoto(new ImageIcon("2.jpg")));
	images.add(new ProjPhoto(new ImageIcon("3.jpg")));
	images.add(new ProjPhoto(new ImageIcon("4.jpg")));
	 */
	controlPane = new JPanel();
	controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.PAGE_AXIS));

	createMenus(); // This function creates the menus

	createInfoSpace();

	createButtons(); // Wonder what this function does

	if (images.size() > 0) {
	    changeImage();
	}

	mainWindow.add(controlPane, BorderLayout.SOUTH);

	mainWindow.add(scrollPane);
	//this.setPreferredSize(preferredSize);
	this.setMinimumSize(getSize());

    }

    void changeImage() {
	imageLabel.setIcon(images.get(imageNumber).getIcon());
	descriptionTextArea.setText(images.get(imageNumber).description);
	dateTextField.setText(images.get(imageNumber).date);
	currPageText.setText(Integer.toString(imageNumber + 1));
    }

    private void openLibrary() {
	try {
	    FileInputStream fileIn = new FileInputStream("photolib");
	    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	    images = (ArrayList) objectIn.readObject();
	    objectIn.close();
	    fileIn.close();
	}
	catch (IOException e) {
	    System.out.println("No library detected, gallery set to empty.");
	    return;
	}
	catch (ClassNotFoundException c) {
	    System.out.println("Class not found");
	    c.printStackTrace();
	    return;
	}

    }

    private void addPhoto(String inFile) {
	try {
	    ImageIcon newPhoto = new ImageIcon(inFile);
	    images.add(new ProjPhoto(newPhoto));
	    totalPageText.setText(Integer.toString(images.size()));
	    imageNumber = images.size() - 1;
	    changeImage();
	    nextButton.setEnabled(false);
	    prevButton.setEnabled(true);

	}
	catch (Throwable t) {
	    t.printStackTrace();
	    System.err.println("You suck at IO");
	}
    }

    private void createMenus() {
	// Create menu system and fill with wonderous sub-menus

	topBar = new JMenuBar();
	fileMenu = new JMenu("File");
	topBar.add(fileMenu);
	saveMenuItem = new JMenuItem("Save");
	exitMenuItem = new JMenuItem("Exit");
	fileMenu.add(saveMenuItem);

	saveMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    FileOutputStream fileOut = new FileOutputStream("photolib");
		    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
		    objectOut.writeObject(images);
		    objectOut.close();
		    fileOut.close();
		}
		catch (IOException ioe) {
		    ioe.printStackTrace();
		}
	    }
	});

	fileMenu.add(exitMenuItem);

	exitMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e
	    ) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit? Changes may not be saved!", "Warning", dialogButton);
		if (dialogResult == JOptionPane.YES_OPTION) {
		    System.exit(0);
		}
	    }
	});

	viewMenu = new JMenu("View");
	browseMenuItem = new JMenuItem("Browse");

	browseMenuItem.addActionListener(
		new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e
	    ) {
		buttonPane.setVisible(false);
		descriptionTextArea.setEditable(false);
		dateTextField.setEditable(false);
	    }
	}
	);
	maintainMenuItem = new JMenuItem("Maintain");

	maintainMenuItem.addActionListener(
		new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e
	    ) {
		buttonPane.setVisible(true);
		descriptionTextArea.setEditable(true);
		dateTextField.setEditable(true);
	    }
	}
	);
	topBar.add(viewMenu);

	viewMenu.add(browseMenuItem);

	viewMenu.add(maintainMenuItem);

	this.setJMenuBar(topBar);
    }

    private void createInfoSpace() {
	JPanel descriptionPane = new JPanel();
	descriptionPane.setLayout(new FlowLayout(FlowLayout.LEFT));

	JLabel descriptionLabel = new JLabel("Description:");
	descriptionTextArea = new JTextArea(4, 20);
	descriptionTextArea.setEditable(false);

	descriptionPane.add(descriptionLabel);
	descriptionPane.add(descriptionTextArea);

	JPanel datePane = new JPanel();
//		datePane.setLayout(new FlowLayout(FlowLayout.LEFT));
//		datePane.setLayout(new BoxLayout(datePane, BoxLayout.LINE_AXIS));

	JLabel dateLabel = new JLabel("Date:");
	dateLabel.setPreferredSize(new Dimension(descriptionLabel.getPreferredSize().width, dateLabel.getPreferredSize().height));
	dateTextField = new JTextField("1/1/2014");
	dateTextField.setEditable(false);
	dateTextField.setPreferredSize(new Dimension(100, 25));
	datePane.add(dateLabel);
	datePane.add(dateTextField);
	//datePane.add(Box.createHorizontalGlue());

	deleteButton = new JButton("Delete");
	deleteButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(mainWindow, "All art is good art. Don't be snobbish.");
	    }
	});

	saveButton = new JButton("Save");
	saveButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		images.get(imageNumber).setDescription(descriptionTextArea.getText());
		images.get(imageNumber).setDate(dateTextField.getText());

	    }
	});

	addButton = new JButton("Add Items");
	addButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.changeToParentDirectory();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"JPG & GIF Images", "jpg", "gif");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    addPhoto(chooser.getSelectedFile().getAbsolutePath());
		}
	    }
	});

	buttonPane = new JPanel();
	buttonPane.add(deleteButton);
	buttonPane.add(saveButton);
	buttonPane.add(addButton);

	JPanel leftRightPane = new JPanel();
	leftRightPane.setLayout(new BorderLayout());
	leftRightPane.add(datePane, BorderLayout.WEST);
	leftRightPane.add(buttonPane, BorderLayout.EAST);
	buttonPane.setVisible(false);
	controlPane.add(descriptionPane);
	controlPane.add(leftRightPane);

    }

    private void createButtons() {
	Container bottomPanel = new JPanel();
	Container bottomPanelWest = new JPanel();
	Container bottomPanelEast = new JPanel();
	FlowLayout flleft = new FlowLayout(FlowLayout.LEFT, 5, 20);
	FlowLayout flright = new FlowLayout(FlowLayout.RIGHT, 5, 20);
	bottomPanelWest.setLayout(new FlowLayout(FlowLayout.LEFT));
	bottomPanelEast.setLayout(new FlowLayout(FlowLayout.RIGHT));
	bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));

	// Create buttons and text fields
	currPageText = new JTextField("1");
	totalPageText = new JTextArea();
	try {
	    totalPageText.setText(String.valueOf(images.size()));
	}
	catch (NullPointerException n) {
	    totalPageText.setText("0");
	}
	prevButton = new JButton("<Prev");
	nextButton = new JButton("Next>");
	prevButton.setEnabled(false);

	/*
	JSlider zoomSlide = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
	zoomSlide.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		System.out.println("Value : "
			+ ((JSlider) e.getSource()).getValue());
	    }
	});
	 */
	// Init action listeners to check for button presses
	prevButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		prevButtonAction();
	    }
	});
	nextButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		nextButtonAction();
	    }
	});

	bottomPanelWest.add(currPageText);
	bottomPanelWest.add(totalPageText);
	bottomPanelWest.add(prevButton);
	bottomPanelWest.add(nextButton);
	// bottomPanelEast.add(zoomSlide);

	bottomPanel.add(bottomPanelWest);
	bottomPanel.add(Box.createHorizontalGlue());
	bottomPanel.add(bottomPanelEast);

	controlPane.add(bottomPanel);

	if (images.size() < 2) {
	    nextButton.setEnabled(false);
	}

    }

    /*
    private ImageIcon resizeImage(ImageIcon image) {
	int height = image.getIconHeight();
	int width = image.getIconWidth();
	Image tempimg = image.getImage(); // transform it
	Image newimg = tempimg.getScaledInstance(width / 2, height / 2, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
	return new ImageIcon(newimg);
    }

    private void importPhotos() {
	String currentDirectory;
	File file = new File(".");
	currentDirectory = file.getAbsolutePath();

    }

     */
    @Override
    public void actionPerformed(ActionEvent evt) {
	System.out.println("Action?");
    }

    private void prevButtonAction() {
	if (imageNumber > 0) {
	    imageNumber--;
	    if (imageNumber < images.size() - 1) {
		nextButton.setEnabled(true);
	    }
	    if (imageNumber <= 0) {
		prevButton.setEnabled(false);
	    }
	    changeImage();
	    //imageLabel.setIcon(images.get(imageNumber).getIcon());
	}
	else {
	    System.out.println("Prev button" + Integer.toString(imageNumber));
	}
    }

    private void nextButtonAction() {
	if (imageNumber < images.size()) {
	    imageNumber++;
	    if (imageNumber == 1) {
		prevButton.setEnabled(true);
	    }
	    else if (imageNumber >= images.size() - 1) {
		nextButton.setEnabled(false);
	    }
	    changeImage();
	    //imageLabel.setIcon(images.get(imageNumber).getIcon());
	}
	// else {
	//     System.out.println("Next button" + Integer.toString(imageNumber));
	// }
    }

    @Override
    public Dimension getMinimumSize() {
	return new Dimension(500, 500);
    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(750, 750);
    }

    public static void main(String[] args) {

	JFrame frame = new photoViewer("Photo Album");

	frame.pack();

	frame.setVisible(true);

	System.out.println("Program begins...");

	frame.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
    }

}
