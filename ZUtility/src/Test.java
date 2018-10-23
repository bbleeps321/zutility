
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Test
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
//        CalendarComponent cc = new CalendarComponent();
//        contentPane.add(cc, BorderLayout.CENTER);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
	}
}
