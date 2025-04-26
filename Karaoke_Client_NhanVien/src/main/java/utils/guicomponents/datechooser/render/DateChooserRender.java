package utils.guicomponents.datechooser.render;

import java.util.Date;

import utils.guicomponents.daterangechooser.DateBetween;
import utils.guicomponents.daterangechooser.DateRangeChooser;

public interface DateChooserRender {
    public String renderLabelCurrentDate(DateRangeChooser dateRangeChooser, Date date);

    public String renderTextFieldDate(DateRangeChooser dateRangeChooser, Date date);

    public String renderTextFieldDateBetween(DateRangeChooser dateRangeChooser, DateBetween dateBetween);

    public String renderDateCell(DateRangeChooser dateRangeChooser, Date date);
}
