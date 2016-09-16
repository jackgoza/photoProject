

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;



/**
 *
 * @author jack
 */
public class photoViewer extends JFrame implements ActionListener {
    
    public photoViewer(String title){
        super(title);
        
        JButton incrementButton = new JButton("increment");
        // Register this class to handle events from
        // the button.
        incrementButton.addActionListener(this);
        
        Container contentPane = getContentPane();
        
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        
        ImageIcon image = new ImageIcon("1.jpg");
        imageLabel.setIcon(image);
        
        contentPane.add(scrollPane);
        
        Container bottomPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT,5, 20);
        bottomPanel.setLayout(fl);
        
        JTextField currPageText = new JTextField("9");
        JTextArea totalPageText = new JTextArea("21");
        JButton prevButton = new JButton("<Prev");
        JButton nextButton = new JButton("Next>");
        
        bottomPanel.add(currPageText);
        bottomPanel.add(totalPageText);
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        
        contentPane.add(bottomPanel,BorderLayout.SOUTH);

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
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Button pressed");
    }
}
