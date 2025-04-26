
package utils.guicomponents.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author RAVEN
 */
public class TableActionCellRender extends DefaultTableCellRenderer {
	private boolean tonTaiNutXoa;
	private boolean tonTaiNutSua;
	
	public TableActionCellRender(boolean tonTaiNutXoa, boolean tonTaiNutSua) {
		this.tonTaiNutXoa = tonTaiNutXoa;
		this.tonTaiNutSua = tonTaiNutSua;
	}
	public TableActionCellRender(boolean tonTaiNutXoa) {
		this.tonTaiNutXoa = tonTaiNutXoa;
		this.tonTaiNutSua = true;
	}

	@Override
	public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row,
			int column) {
		Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
		PanelAction action = new PanelAction(tonTaiNutXoa,tonTaiNutSua);
		action.setBackground(jtable.getCellRenderer(row, column -1).getTableCellRendererComponent(jtable, action, isSeleted, bln1, row, column).getBackground());
		action.setEnabled(false);
		return action;
	}
}
