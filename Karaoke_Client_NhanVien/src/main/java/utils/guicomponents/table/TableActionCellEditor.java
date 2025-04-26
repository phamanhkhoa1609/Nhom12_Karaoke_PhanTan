package utils.guicomponents.table;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author RAVEN
 */
public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;
    private boolean tonTaiNutXoa;
	private boolean tonTaiNutSua;
    public TableActionCellEditor(TableActionEvent event,boolean tonTaiNutXoa, boolean tonTaiNutSua) {
        super(new JCheckBox());
        this.event = event;
        this.tonTaiNutXoa = tonTaiNutXoa;
        this.tonTaiNutSua = tonTaiNutSua;
    }
    
    public TableActionCellEditor(TableActionEvent event,boolean tonTaiNutXoa) {
        super(new JCheckBox());
        this.event = event;
        this.tonTaiNutXoa = tonTaiNutXoa;
        this.tonTaiNutSua = true;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelAction action = new PanelAction(tonTaiNutXoa,tonTaiNutSua);
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
