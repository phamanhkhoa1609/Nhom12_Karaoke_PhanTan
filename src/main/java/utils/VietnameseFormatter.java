package utils;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class VietnameseFormatter {
	public static String dinhDangTien(long amount) {
		// Chuyển đổi số tiền thành chuỗi định dạng x,xxxVND
		String formattedAmount = String.format("%,3dVND", amount);
		return formattedAmount;
	}

	public static long dinhDangTienNguoc(String formattedAmount) {
	    // Loại bỏ toàn bộ ký tự không phải số từ chuỗi đầu vào
	    String amountString = formattedAmount.replaceAll("\\D", "").trim();
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