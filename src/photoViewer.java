

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;



/**
 *
 * @author jack
 */
public class photoViewer extends JFrame implements ActionListener {
    
    JButton nextButton = null;
    JButton prevButton = null;
    JTextField currPageText = null;
    JTextArea totalPageText = null;
    JLabel imageLabel = null;
    
    int imageNumber = 0;
    
    public photoViewer(String title){
        super(title);
        
        JButton incrementButton = new JButton("increment");
        // Register this class to handle events from
        // the button.
        incrementButton.addActionListener(this);
        
        Container contentPane = getContentPane();
        
        imageLabel = new JLabel("", SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        
        ImageIcon image = new ImageIcon("1.jpg");
        imageLabel.setIcon(image);
        
        contentPane.add(scrollPane);
        
        Container bottomPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT,5, 20);
        bottomPanel.setLayout(fl);
        
        currPageText = new JTextField("0");
        totalPageText = new JTextArea("0");
        prevButton = new JButton("<Prev");
        prevButton.addActionListener(this);
        nextButton = new JButton("Next>");
        nextButton.addActionListener(this);
        
        bottomPanel.add(currPageText);
        bottomPanel.add(totalPageText);
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        
        contentPane.add(bottomPanel,BorderLayout.SOUTH);

    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("ACTION");
        if (evt.getSource() == nextButton){
            imageNumber++;
            currPageText.setText(Integer.toString(imageNumber));
            System.out.println(imageNumber);
        }
        else if (evt.getSource() == prevButton){
            if(imageNumber > 0){
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