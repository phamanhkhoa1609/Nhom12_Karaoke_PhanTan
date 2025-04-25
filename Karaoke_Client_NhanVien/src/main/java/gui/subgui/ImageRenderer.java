package gui.subgui;

import javax.swing.*;
import javax.swing.table.*;

public class ImageRenderer extends DefaultTableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void setValue(Object value) {
        if (value instanceof ImageIcon) {
            setIcon((ImageIcon) value);
        } else {
            setText((value == null) ? "" : value.toString());
            setIcon(null);
        }
    }
}
