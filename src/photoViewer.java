
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;



/**
 *
 * @author jack
 */
public class photoViewer extends JFrame implements ActionListener, Serializable {
    
    JButton nextButton = null;
    JButton prevButton = null;
    JTextField currPageText = null;
    JTextArea totalPageText = null;
    JLabel imageLabel = null;
    ArrayList<BufferedImage> images = null;
    
    int imageNumber = 1;
    
    public photoViewer(String title) // Constructor
    {
        
        // Set title to string passed in
        super(title);
        
        Container contentPane = getContentPane();
        
        // Create button to hold image
        // (TODO: Bound the scroll pane on init)
        imageLabel = new JLabel("", SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        
        // This is temporary (TODO: INIT TO IMAGE 1)
        ImageIcon image = new ImageIcon("1.jpg");
        imageLabel.setIcon(image);
        
        contentPane.add(scrollPane);
        
        // Create bottom panel to hold buttons, numbers

        Container bottomPanel = new JPanel();
        Container bottomPanelWest = new JPanel();
        Container bottomPanelEast = new JPanel();
        FlowLayout flleft = new FlowLayout(FlowLayout.LEFT,5, 20);
        FlowLayout flright = new FlowLayout(FlowLayout.RIGHT,5, 20);
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
        JSlider zoomSlide= new JSlider(JSlider.HORIZONTAL,0,100,10);
        zoomSlide.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            System.out.println("Value : " 
            + ((JSlider)e.getSource()).getValue());
         }
      });
    
        // Init action listeners to check for button presses
        prevButton.addActionListener(this);
        nextButton.addActionListener(this);
        
        bottomPanelWest.add(currPageText);
        bottomPanelWest.add(totalPageText);
        bottomPanelWest.add(prevButton);
        bottomPanelWest.add(nextButton);
        bottomPanelEast.add(zoomSlide);
        

        bottomPanel.add(bottomPanelWest);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(bottomPanelEast);

        contentPane.add(bottomPanel,BorderLayout.SOUTH);

    }
    
    private void importPhotos(String cwd){
        
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("ACTION");
        if (evt.getSource() == nextButton){
            if (imageNumber < 5){
                imageNumber++;
                currPageText.setText(Integer.toString(imageNumber));
                if (imageNumber == 2){
                    prevButton.setEnabled(true);
                }
            }

        }
        else if (evt.getSource() == prevButton){
            if(imageNumber > 1){
                imageNumber--;
                currPageText.setText(Integer.toString(imageNumber));
                if (imageNumber == 4){
                    nextButton.setEnabled(true);
                }
            }
        }
        if (imageNumber <= 1){
            prevButton.setEnabled(false);
        }
        else if (imageNumber >= 5){
            nextButton.setEnabled(false);
        }
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