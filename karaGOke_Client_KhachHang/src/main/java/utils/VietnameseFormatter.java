package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import utils.guicomponents.datechooser.SelectedDate;
import utils.guicomponents.daterangechooser.DateBetween;


public class VietnameseFormatter {
	public static String dinhDangTien(long amount) {
		// Chuyển đổi số tiền thành chuỗi định dạng x,xxxVND
		String formattedAmount = String.format("%,3dVND", amount);
		return formattedAmount;
	}

	public static long dinhDangTienNguoc(String formattedAmount) {
	    // Loại bỏ toàn bộ ký tự không phải số từ chuỗi đầu vào, bỏ qua dấu trừ
		String amountString = formattedAmount.replaceAll("[^0-9-]", "").trim();
	    return Long.parseLong(amountString);
	}

	public static boolean kiemTraTonTaiTep(String filePath) {
		return new File(filePath).exists();
	}

	public static long dinhDangSoTienLe(double amount) {
		return Double.valueOf(Math.floor(amount / 1000) * 1000).longValue();
	}

	public static SelectedDate dinhDangNgaySelectedDate(LocalDate localDate) {
		SelectedDate selectedDate = new SelectedDate();
		selectedDate.setDay(localDate.getDayOfMonth());
		selectedDate.setMonth(localDate.getMonthValue());
		selectedDate.setYear(localDate.getYear());
		return selectedDate;
	}
	
	public static String dinhDangNgayStringYMD(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String dinhDangNgayStringYMD_CSDL(DateBetween dateBetween) {
        String dateFrom = dinhDangNgayStringYMD(dateBetween.getFromDate());
        String dateTo = dinhDangNgayStringYMD(dateBetween.getToDate());
        return " STR_TO_DATE(thoigianlap, '%Y-%m-%d') BETWEEN '"+dateFrom+"' AND '"+dateTo+"'";
    }

	public static String dinhDangNgayString(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public static String dinhDangNgayThoiGianString(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
	}

	public static String dinhDangThoiGianString(LocalTime localTime) {
		return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
	}
	
	

	public static int tinhKhoangThoiGian(LocalTime time, LocalDateTime date) {
		long diffInHours = ChronoUnit.HOURS.between(time, date);
		int gio = (int) Math.abs ((int) Math.ceil(diffInHours));
		if(gio == 0) gio = 1;
		return gio;
	}

	public static String catChuoi(String input, int maxLength) {
		if (input.length() <= maxLength) {
			return input;
		} else {
			return input.substring(0, maxLength) + "...";
		}
	}

	public static LocalTime dinhDangThoiGianLocalTime(String thoiGian) {
		int gio = Integer.parseInt(thoiGian.split(":")[0]);
		int phut = Integer.parseInt(thoiGian.split(":")[1]);
		if(gio==24) gio = 0;
		return LocalTime.of(gio, phut);
	}
	public static LocalDateTime dinhDangNgayThoiGianLocalDateTime(String thoiGian) {
        try {
            // Định dạng chuỗi thời gian
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            
            // Chuyển chuỗi thành đối tượng LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(thoiGian, formatter);
            
            return localDateTime;
        } catch (DateTimeParseException e) {
            System.out.println("Không thể chuyển đổi chuỗi '" + thoiGian + "' thành LocalDateTime.");
            return null;
        }
    }
}