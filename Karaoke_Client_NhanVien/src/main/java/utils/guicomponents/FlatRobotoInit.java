package utils.guicomponents;

import java.awt.Font;

import javax.swing.UIManager;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;


public class FlatRobotoInit {
	public static void init() {
		FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
	}
}
