package Run;

import java.awt.EventQueue;

import gui.ChinhFrame;
import utils.guicomponents.FlatRobotoInit;

public class KhoiDongMayChu {
	public static void main(String[] args) {
		FlatRobotoInit.init();
		// Sử dụng EventQueue.invokeLater và hiện giao diện, khi tắt thì sẽ đóng kết nối RMI và Database
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			ChinhFrame frame = new ChinhFrame();
			frame.setVisible(true);
			}
		});
		
	}
}
