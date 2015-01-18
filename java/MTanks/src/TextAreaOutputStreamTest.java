

import java.awt.BorderLayout;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TextAreaOutputStreamTest extends JPanel {

   private JTextArea textArea = new JTextArea(30, 30);
   private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(
         textArea, "");

   public TextAreaOutputStreamTest() {
	   textArea.setEditable(false);
      setLayout(new BorderLayout());
      add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
      System.setOut(new PrintStream(taOutputStream));
   }
}