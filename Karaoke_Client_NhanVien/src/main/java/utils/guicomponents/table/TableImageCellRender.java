
package utils.guicomponents.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableImageCellRender extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected,
			boolean bln1, int row, int column) {
		Component com = super.getTableCellRendererComponent(jtable, o, isSelected, bln1, row, column);
		JLabel lbl = new JLabel((ImageIcon) o);
		lbl.setBorder(null);
		lbl.setOpaque(false);
		JPanel panel = new JPanel();
		//if(column%2==1) lbl.setBackground(jtable.getcModel());
		panel.setBackground(jtable.getCellRenderer(row, column + 1).getTableCellRendererComponent(jtable, lbl, isSelected, bln1, row, column).getBackground());
		panel.add(lbl);
		panel.setEnabled(false);
		return panel;
	}
}
