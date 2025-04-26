package gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import gui.subgui.FormManager;
import gui.subgui.MyDeskstopPane;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.optionmessage.GlassPanePopup;

import javax.swing.*;
import java.awt.*;

public class ChinhFrame extends JFrame {

    private MyDeskstopPane myDeskstopPane;

    public ChinhFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setSize(new Dimension(1366, 768));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setTitle("KaraGOke");
		setIconImage(new MyImageIcon("src/main/resources/images/icons/applogo.png", 500, 500, 999).getImage());
		myDeskstopPane = new MyDeskstopPane(this);
		getContentPane().add(myDeskstopPane);
		GlassPanePopup.install(this);
        FormManager.getInstance().setDesktopPane(myDeskstopPane);
        
    }
}
