package utils.guicomponents.daterangechooser.listener;

import java.util.Date;

import utils.guicomponents.daterangechooser.DateBetween;

public interface DateChooserListener {
    public void dateChanged(Date date, DateChooserAction action);

    public void dateBetweenChanged(DateBetween date, DateChooserAction action);
}
