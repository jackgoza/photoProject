
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
        imageLabel = new JLabel("", SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        
        // This is temporary
        ImageIcon image = new ImageIcon("1.jpg");
        imageLabel.setIcon(image);
        
        contentPane.add(scrollPane);
        
        // Create bottom panel to hold buttons, numbers
        Container bottomPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT,5, 20);
        bottomPanel.setLayout(fl);
        
        // Create buttons and text fields
        currPageText = new JTextField("1");
        totalPageText = new JTextArea("5");
        prevButton = new JButton("<Prev");
        nextButton = new JButton("Next>");
        
        // Init action listeners to check for button presses
        prevButton.addActionListener(this);
        nextButton.addActionListener(this);
        
        bottomPanel.add(currPageText);
        bottomPanel.add(totalPageText);
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        
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
            }

        }
        else if (evt.getSource() == prevButton){
            if(imageNumber > 1){
                imageNumber--;
                currPageText.setText(Integer.toString(imageNumber));

            }
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