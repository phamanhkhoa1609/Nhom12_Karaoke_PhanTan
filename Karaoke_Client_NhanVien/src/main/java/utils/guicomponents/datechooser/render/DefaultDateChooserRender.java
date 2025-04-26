package utils.guicomponents.datechooser.render;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import utils.guicomponents.daterangechooser.DateBetween;
import utils.guicomponents.daterangechooser.DateRangeChooser;

public class DefaultDateChooserRender implements DateChooserRender {

    @Override
    public String renderLabelCurrentDate(DateRangeChooser dateRangeChooser, Date date) {
        return "Today : " + dateRangeChooser.getDateFormat().format(date);
    }

    @Override
    public String renderTextFieldDate(DateRangeChooser dateRangeChooser, Date date) {
        return dateRangeChooser.getDateFormat().format(date);
    }

    @Override
    public String renderTextFieldDateBetween(DateRangeChooser dateRangeChooser, DateBetween dateBetween) {
        if (dateBetween.getToDate() != null) {
            if (dateBetween.getFromDate().compareTo(dateBetween.getToDate()) == 0) {
                return dateRangeChooser.getDateFormat().format(dateBetween.getFromDate());
            } else {
                return dateRangeChooser.getDateFormat().format(dateBetween.getFromDate()) + dateRangeChooser.getBetweenCharacter() + dateRangeChooser.getDateFormat().format(dateBetween.getToDate());
            }
        } else {
            return dateRangeChooser.getDateFormat().format(dateBetween.getFromDate());
        }
    }

    @Override
    public String renderDateCell(DateRangeChooser dateRangeChooser, Date date) {
        DateFormat df = new SimpleDateFormat("d");
        return df.format(date);
    }
}
