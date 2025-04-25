package gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import entities.KhachHang;
import entities.NguoiDung;
import entities.TaiKhoan;
import entities.enums.GioiTinh;
import gui.subgui.*;
import utils.ClientIDAO;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.optionmessage.GlassPanePopup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

public class ChinhDeskstop extends JFrame {

    private MainForm mainForm;
	private ClientIDAO clientIDAO;
	private NguoiDung nguoiDung;
	
    public ClientIDAO getClientIDAO() {
		return clientIDAO;
	}

	public ChinhDeskstop(NguoiDung nguoiDung) {
    	clientIDAO = new ClientIDAO();
    	mainForm = new MainForm(this,nguoiDung,clientIDAO);
		this.nguoiDung = nguoiDung;
    	GlassPanePopup.install(this); // Jframe này sẽ nhận MyMessage
        initFrame();
    }
    
	private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        setTitle("KaraGOke");
        setIconImage(new MyImageIcon("src/main/resources/images/icons/applogo.png",500,500,999).getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        getContentPane().add(mainForm);
        addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					System.out.println("Ngắt kết nối");
					clientIDAO.getTaiKhoanIDAO().ngatKetNoi(nguoiDung.getMaND(),clientIDAO.getMyIP());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
    }
}
