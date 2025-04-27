package gui.subgui;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import raven.swing.blur.BlurChild;
import raven.swing.blur.style.Style;
import raven.swing.blur.style.StyleBorder;
import raven.swing.blur.style.StyleOverlay;
import utils.guicomponents.MyPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class FormManager {

    private static FormManager instance;
    private JDesktopPane desktopPane;

    public static FormManager getInstance() {
        if (instance == null) {
            instance = new FormManager();
        }
        return instance;
    }

    private FormManager() {

    }

    public void setDesktopPane(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
    }

    public JInternalFrame showForm(String title, Component component) {
    	JInternalFrame jInternalFrame = showForm(title, component, 1000,1000);
    	try {
			jInternalFrame.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return jInternalFrame;
    }
    public JInternalFrame showForm(String title, Component component,int width,int height) {
        //JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
        JInternalFrame frame = createBlurFrame(title, component);
        frame.setLayout(new BorderLayout());
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(component);
        if(component instanceof MyPanel) {
        	((MyPanel) component).setManHinhChua(frame);
        	((MyPanel) component).KhoiTaoGiaoDien();
        }
        
        frame.add(jScrollPane,BorderLayout.CENTER);
        frame.setSize(new Dimension(width, height));
        frame.setFrameIcon(null);
        frame.setLocation(JDesktopPane.WIDTH, JDesktopPane.HEIGHT);
        try {
            frame.setMaximum(false);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        frame.setVisible(true);
        desktopPane.add(frame, 0);
		return frame;
    }


    private JInternalFrame createBlurFrame(String title, Component component) {
        JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
        BlurChild child = new BlurChild(new Style()
                .setBlur(10)
                .setBorder(new StyleBorder(10)
                        .setBorderWidth(1.2f)
                        .setOpacity(0.1f)
                        .setMargin(new Insets(10, 0, 0, 0))
                        .setBorderColor(new Color(255, 255, 255))
                )
                .setOverlay(new StyleOverlay(new Color(0, 0, 0), 0.03f))
        );
        child.setLayout(new BorderLayout());
        child.add(component);
        frame.setFrameIcon(null);
        frame.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:0,0,0,0");
        frame.add(child);
        return frame;
    }
}
