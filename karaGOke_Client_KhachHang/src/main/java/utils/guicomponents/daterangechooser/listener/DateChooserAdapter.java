package utils.guicomponents.daterangechooser.listener;


import java.util.Date;

import utils.guicomponents.daterangechooser.DateBetween;

public abstract class DateChooserAdapter implements DateChooserListener {

    @Override
    public void dateChanged(Date date, DateChooserAction action) {
    }

    @Override
    public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
    }
}
