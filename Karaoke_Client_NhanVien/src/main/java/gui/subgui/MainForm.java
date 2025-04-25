package gui.subgui;

import net.miginfocom.swing.MigLayout;
import raven.swing.blur.BlurBackground;
import raven.swing.blur.style.StyleOverlay;
import utils.ClientIDAO;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.datechooser.Title;

import javax.swing.*;

import entities.NguoiDung;
import gui.ChinhDeskstop;

import java.awt.*;
import java.util.Random;

public class MainForm extends BlurBackground {
	public MainForm(ChinhDeskstop chinhDeskstop, NguoiDung nguoiDung,ClientIDAO clientIDAO) {
        setOverlay(new StyleOverlay(new Color(50, 50, 50), 0.4f));
        setImage(new MyImageIcon("src/main/resources/images/karaokebackground.jpg").getImage().getScaledInstance((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), Image.SCALE_SMOOTH));
        setLayout(new MigLayout("fill,insets 0 0 6 6", "[fill]", "[fill]"));

        systemMenu = new SystemMenu(chinhDeskstop,nguoiDung, clientIDAO);
        title = new Title();
        desktopPane = new JDesktopPane();
        desktopPane.setLayout(null);


        desktopPane.setOpaque(false);
        FormManager.getInstance().setDesktopPane(desktopPane);

        add(systemMenu, "dock west,gap 6 6 6 6,width 280!");
        add(title, "dock north,gap 0 6 6 6,height 50!");
        add(desktopPane);
    }




	private SystemMenu systemMenu;
    private Title title;
    private JDesktopPane desktopPane;
}
