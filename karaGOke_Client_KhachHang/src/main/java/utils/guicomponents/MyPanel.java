package utils.guicomponents;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import entities.NguoiDung;
import gui.ChinhFrame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raven.swing.blur.BlurChild;
import utils.ClientIDAO;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class MyPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ClientIDAO clientIDAO;
	protected JInternalFrame manHinhChua;
	protected JInternalFrame tienManHinh;
	protected ChinhFrame chinhFrame;
	protected NguoiDung nguoiDung;
	public abstract void KhoiTaoGiaoDien();
}
