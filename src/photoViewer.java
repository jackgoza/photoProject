
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author jack
 */
public class photoViewer extends JFrame implements ActionListener, Serializable {

    Container mainWindow;
    JPanel controlPane;
    JMenuBar topBar = null;
    JMenu fileMenu = null;
    JMenuItem saveMenuItem = null;
    JMenuItem exitMenuItem = null;
    JMenu viewMenu = null;
    JMenuItem browseMenuItem = null;
    JMenuItem maintainMenuItem = null;
    JButton nextButton = null;
    JButton prevButton = null;
    JTextField currPageText = null;
    JTextArea totalPageText = null;
    JLabel imageLabel = null;
    JPanel buttonPane;
    JTextArea descriptionTextArea;
    JTextField dateTextField;
    ArrayList<BufferedImage> images = null;

    int imageNumber = 1;

    public photoViewer(String title) // Constructor
    {

	// Set title to string passed in
	super(title);
	mainWindow = getContentPane();

	// Create button to hold image
	// (TODO: Bound the scroll pane on init)
	imageLabel = new JLabel("", SwingConstants.CENTER);
	JScrollPane scrollPane = new JScrollPane(imageLabel);

	// This is temporary (TODO: INIT TO IMAGE 1)
	ImageIcon image = new ImageIcon("1.jpg");
	//resizeImage();
	imageLabel.setIcon(image);

	controlPane = new JPanel();
	controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.PAGE_AXIS));

	createMenus(); // This function creates the menus

	createInfoSpace();

	createButtons(); // Wonder what this function does

	mainWindow.add(controlPane, BorderLayout.SOUTH);

	mainWindow.add(scrollPane);
	//this.setPreferredSize(preferredSize);
	this.setMinimumSize(getSize());

    }

    private void createMenus() {
	// Create menu system and fill with wonderous sub-menus

	topBar = new JMenuBar();
	fileMenu = new JMenu("File");
	topBar.add(fileMenu);
	saveMenuItem = new JMenuItem("Save");
	exitMenuItem = new JMenuItem("Exit");
	fileMenu.add(saveMenuItem);
	fileMenu.add(exitMenuItem);
	viewMenu = new JMenu("View");
	browseMenuItem = new JMenuItem("Browse");
	browseMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		buttonPane.setVisible(false);
		descriptionTextArea.setEditable(false);
		dateTextField.setEditable(false);
	    }
	});
	maintainMenuItem = new JMenuItem("Maintain");
	maintainMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		buttonPane.setVisible(true);
		descriptionTextArea.setEditable(true);
		dateTextField.setEditable(true);
	    }
	});
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
	datePane.add(dateLabel);
	datePane.add(dateTextField);
	//datePane.add(Box.createHorizontalGlue());
	buttonPane = new JPanel();
	buttonPane.add(new JButton("Delete"));
	buttonPane.add(new JButton("Save Changes"));
	buttonPane.add(new JButton("Add Photo"));

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
	totalPageText = new JTextArea("5");
	prevButton = new JButton("<Prev");
	nextButton = new JButton("Next>");
	prevButton.setEnabled(false);

	// Create zoom slider (TO BE IMPLIMENTED)
	JSlider zoomSlide = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
	zoomSlide.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		System.out.println("Value : "
			+ ((JSlider) e.getSource()).getValue());
	    }
	});

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
	bottomPanelEast.add(zoomSlide);

	bottomPanel.add(bottomPanelWest);
	bottomPanel.add(Box.createHorizontalGlue());
	bottomPanel.add(bottomPanelEast);

	controlPane.add(bottomPanel);

    }

    private ImageIcon resizeImage(ImageIcon image) {
	int height = image.getIconHeight();
	int width = image.getIconWidth();
	Image tempimg = image.getImage(); // transform it
	Image newimg = tempimg.getScaledInstance(width / 2, height / 2, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
	return new ImageIcon(newimg);
    }

    private void importPhotos(String cwd) {

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
	System.out.println("Action?");
    }

    private void prevButtonAction() {
	if (imageNumber > 1) {
	    imageNumber--;
	    currPageText.setText(Integer.toString(imageNumber));
	    if (imageNumber == 4) {
		nextButton.setEnabled(true);
	    }
	    else if (imageNumber <= 1) {
		prevButton.setEnabled(false);
	    }
	}
	else {
	    throw new Error("Prev button pressed when should be disabled!");
	}
    }

    private void nextButtonAction() {
	if (imageNumber < 5) {
	    imageNumber++;
	    currPageText.setText(Integer.toString(imageNumber));
	    if (imageNumber == 2) {
		prevButton.setEnabled(true);
	    }
	    else if (imageNumber == 5) {
		nextButton.setEnabled(false);
	    }
	}
	else {
	    throw new Error("Next button pressed when should be disabled!");
	}
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
