package run;

import java.awt.EventQueue;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import gui.ChinhFrame;

public class KhoiDongMayKhachKhachHang {
	public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new ChinhFrame().setVisible(true));
    }
}
