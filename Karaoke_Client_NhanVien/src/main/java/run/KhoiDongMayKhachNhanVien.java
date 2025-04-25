package run;

import java.awt.EventQueue;

import gui.DangNhapFrame;
import utils.guicomponents.FlatRobotoInit;

public class KhoiDongMayKhachNhanVien {
	public static void main(String[] args) {
		FlatRobotoInit.init();
		EventQueue.invokeLater(() -> new DangNhapFrame().setVisible(true));
	}
}
