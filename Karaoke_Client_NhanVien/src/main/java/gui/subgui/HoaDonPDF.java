package gui.subgui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import entities.HoaDon;
import entities.KhachHang;
import entities.NhanVien;
import gui.ChinhDeskstop;
import utils.VietnameseFormatter;

public class HoaDonPDF {
	private Document document;
	private HoaDon hoaDon;
	private PdfWriter writer;
	final float fourcol = 142.5f;
	final float threecol = 190f;
	final float twocol = 285f;
	final float twocol200 = twocol + 200f;
	final float twocolumnWidth[] = { twocol, twocol200 };
	final float threeColumnWidth[] = { threecol, threecol, threecol };
	final float fourColumnWidth[] = { fourcol, fourcol, fourcol, fourcol };
	final float fullwidth[] = { threecol * 3 };
	// Font Times New Roman in đen, kích thước h1
	private Font h[] = new Font[5];
	private Font p;
	private Object pdfWriter;
	private Font cellHeader;

	private void thietLapFont() {
		h[1] = FontFactory.getFont("src/main/resources/fonts/SVN-Times New Roman bold.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED, 24);
		h[2] = FontFactory.getFont("src/main/resources/fonts/SVN-Times New Roman bold.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED, 18);
		h[3] = FontFactory.getFont("src/main/resources/fonts/SVN-Times New Roman bold.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED, 14);
		h[4] = FontFactory.getFont("src/main/resources/fonts/SVN-Times New Roman bold.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED, 12);
		p = FontFactory.getFont("src/main/resources/fonts/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED, 12);
		cellHeader = new Font(h[4]);
		cellHeader.setColor(BaseColor.WHITE);
	}

	public HoaDonPDF(HoaDon hoaDon, String fileName) throws Exception {
		super();
		this.hoaDon = hoaDon;
		document = new Document();
		writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

		document.open();
		thietLapFont();
		veLogo();
		veTieuDe();
		document.add(new Chunk(new DottedLineSeparator()));
		veThongTinNhanSu();
		document.add(new Chunk(new DottedLineSeparator()));
		veDanhDachPhong();
		document.add(new Chunk(new DottedLineSeparator()));
		veDanhDachDichVu();
		document.add(new Chunk(new DottedLineSeparator()));
		veThongTinTien();
		document.add(new Chunk(new DottedLineSeparator()));
		Paragraph pCamOn = new Paragraph("Cảm ơn đã sử dụng dịch vụ của chúng tôi", p);
		pCamOn.setAlignment(Element.ALIGN_CENTER);
		document.add(pCamOn);
		document.add(new Phrase("\n"));
		document.add(new LineSeparator());
		document.close();
	}

	public void veTieuDe() throws DocumentException {
		// Tạo một bảng 2 cột
		PdfPTable table = new PdfPTable(2);
		table.setWidths(twocolumnWidth);

		// Phần bên trái
		PdfPCell leftCell = new PdfPCell();
		leftCell.addElement(new Phrase("HÓA ĐƠN", h[1]));
		leftCell.addElement(new Phrase("KaraGOke", h[2]));
		leftCell.addElement(new Phrase("\n", h[2]));
		leftCell.setBorder(Rectangle.NO_BORDER);
		leftCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(leftCell);

		// Phần bên phải
		PdfPCell rightCell = new PdfPCell();
		Paragraph paragraph = new Paragraph();
		paragraph.add(new Phrase("\nSố HD: ", h[4]));
		paragraph.add(new Phrase(hoaDon.getMaHD() + "", p));
		paragraph.add(new Phrase("\nThời gian thanh toán: ", h[4]));
		if (hoaDon.getThoiGianThanhToan() == null) {
			paragraph.add(new Phrase("Chưa thanh toán", p));
		} else {
			paragraph.add(new Phrase(VietnameseFormatter.dinhDangNgayThoiGianString(hoaDon.getThoiGianThanhToan()), p));
		}
		paragraph.setAlignment(Element.ALIGN_CENTER);
		rightCell.addElement(paragraph);
		rightCell.setBorder(Rectangle.NO_BORDER);
		table.addCell(rightCell);
		// Thêm bảng vào tài liệu
		document.add(table);
		// Kẻ đường
	}

	private void veThongTinNhanSu() throws DocumentException {
		NhanVien phucVu = hoaDon.getPhucVu();
		KhachHang khachHang = hoaDon.getKhachHang();

		PdfPTable table = new PdfPTable(2);
		table.setWidths(new float[] { twocol, twocol });

		// Left column
		PdfPCell leftCell = new PdfPCell();
		Paragraph leftParagraph = new Paragraph();
		leftParagraph.add(new Phrase("\nThông tin tiếp tân", h[3]));
		leftParagraph.add(new Phrase("\nMã số", h[4]));
		leftParagraph.add(new Phrase("\n" + hoaDon.getTiepTan().getMaND(), p));
		leftParagraph.add(new Phrase("\nHọ tên", h[4]));
		leftParagraph.add(new Phrase("\n" + hoaDon.getTiepTan().getHoTen(), p));

		leftParagraph.add(new Phrase("\n\nThông tin phục vụ", h[3]));
		if (phucVu == null) {
			leftParagraph.add(new Phrase(": Không có", p));
		} else {
			leftParagraph.add(new Phrase("\nMã số", h[4]));
			leftParagraph.add(new Phrase("\n" + hoaDon.getPhucVu().getMaND(), p));
			leftParagraph.add(new Phrase("\nHọ tên", h[4]));
			leftParagraph.add(new Phrase("\n" + hoaDon.getPhucVu().getHoTen(), p));
		}
		leftParagraph.add(new Phrase("\n", p));
		leftCell.setBorder(Rectangle.NO_BORDER);
		leftCell.addElement(leftParagraph);
		table.addCell(leftCell);
		// Right column
		PdfPCell rightCell = new PdfPCell();
		Paragraph rightParagraph = new Paragraph();
		rightParagraph.add(new Phrase("\nThông tin khách hàng", h[3]));
		if (khachHang == null) {
			rightParagraph.add(new Phrase(": Không có", p));
		} else {
			rightParagraph.add(new Phrase("\nMã số", h[4]));
			rightParagraph.add(new Phrase("\n" + hoaDon.getKhachHang().getMaND(), p));
			rightParagraph.add(new Phrase("\nHọ tên", h[4]));
			rightParagraph.add(new Phrase("\n" + hoaDon.getKhachHang().getHoTen(), p));
			rightParagraph.add(new Phrase("\nSố điện thoại", h[4]));
			rightParagraph.add(new Phrase("\n" + hoaDon.getKhachHang().getSdt(), p));
			rightParagraph.add(new Phrase("\nGiới tính", h[4]));
			rightParagraph.add(new Phrase("\n" + hoaDon.getKhachHang().getGioiTinh().getGioiTinh(), p));
			rightParagraph.add(new Phrase("\nHội viên", h[4]));
			rightParagraph.add(new Phrase("\n" + hoaDon.getKhachHang().getHoiVien().getHoiVien() + "\n", p));
		}
		rightParagraph.add(new Phrase("\n", p));
		rightCell.setBorder(Rectangle.NO_BORDER);
		rightCell.addElement(rightParagraph);
		table.addCell(rightCell);
		document.add(table);
	}

	private void veDanhDachPhong() throws DocumentException {
		Paragraph pTieuDe = new Paragraph("\tDanh sách Phòng\n\n", h[3]);
		pTieuDe.setIndentationLeft(50); // add a 50pt margin to the left side of the paragraph
		document.add(pTieuDe);
		PdfPTable table = new PdfPTable(fourColumnWidth);
		Stream.of("Tên phòng", "Giá phòng", "Số giờ", "Thành tiền").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.BLACK);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitle, cellHeader));
			header.setHorizontalAlignment(columnTitle.equals("Tên phòng") ? Element.ALIGN_LEFT : Element.ALIGN_RIGHT);
			table.addCell(header);
		});
		hoaDon.getDsCTHDP().stream().forEach(cthdp -> {
			int soGio = 1;
			PdfPCell cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_LEFT); // align the left column to the left
			cell.setPhrase(new Phrase(cthdp.getPhong().getTenPhong(), p));
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // align the remaining columns to the right
			cell.setPhrase(new Phrase(VietnameseFormatter.dinhDangTien(cthdp.getDonGia()), p));
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // align the remaining columns to the right
			cell.setPhrase(new Phrase((soGio = VietnameseFormatter.tinhKhoangThoiGian(cthdp.getThoiGianKetThuc(),
					cthdp.getThoiGianBatDau())) + "", p));
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // align the remaining columns to the right
			cell.setPhrase(new Phrase(VietnameseFormatter.dinhDangTien(soGio * cthdp.getDonGia()), p));
			table.addCell(cell);
		});
		document.add(table);
	}

	private void veDanhDachDichVu() throws DocumentException {
		Paragraph pTieuDe = new Paragraph("\tDanh sách Dịch vụ\n\n", h[3]);
		pTieuDe.setIndentationLeft(50); // add a 50pt margin to the left side of the paragraph
		document.add(pTieuDe);
		PdfPTable table = new PdfPTable(fourColumnWidth);
		Stream.of("Tên dịch vụ", "Giá dịch vụ", "Số giờ", "Thành tiền").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.BLACK);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitle, cellHeader));
			header.setHorizontalAlignment(columnTitle.equals("Tên dịch vụ") ? Element.ALIGN_LEFT : Element.ALIGN_RIGHT);
			table.addCell(header);
		});
		hoaDon.getDsCTHDDV().stream().forEach(cthddv -> {
			PdfPCell cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_LEFT); // align the left column to the left
			cell.setPhrase(new Phrase(cthddv.getDichVu().getTenDV(), p));
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // align the remaining columns to the right
			cell.setPhrase(new Phrase(VietnameseFormatter.dinhDangTien(cthddv.getDonGia()), p));
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // align the remaining columns to the right
			cell.setPhrase(new Phrase(cthddv.getSoLuong() + "", p));
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // align the remaining columns to the right
			cell.setPhrase(new Phrase(VietnameseFormatter.dinhDangTien(cthddv.getSoLuong() * cthddv.getDonGia()), p));
			table.addCell(cell);
		});
		document.add(table);
	}

	private void veThongTinTien() throws DocumentException {
		Paragraph pTongTien = new Paragraph("Tổng tiền: " + VietnameseFormatter.dinhDangTien(hoaDon.getTongTien()),
				h[3]);
		pTongTien.setAlignment(Element.ALIGN_RIGHT); // align the paragraph to the right
		pTongTien.setIndentationRight(50);
		document.add(pTongTien);

		String phanTramKhuyenMai = "";
		if (hoaDon.getKhachHang() == null) {
			phanTramKhuyenMai = "0%";
		} else {
			switch (hoaDon.getKhachHang().getHoiVien()) {
			case ĐỒNG:
				phanTramKhuyenMai = "5%";
				break;
			case BẠC:
				phanTramKhuyenMai = "10%";
				break;
			case VÀNG:
				phanTramKhuyenMai = "15%";
				break;
			case BẠCHKIM:
				phanTramKhuyenMai = "20%";
				break;
			case KIMCƯONG:
				phanTramKhuyenMai = "25%";
				break;
			default:
				phanTramKhuyenMai = "0%";
				break;
			}
		}
		Paragraph pKhuyenMai = new Paragraph(
				"Khuyến mãi " + phanTramKhuyenMai + ": " + VietnameseFormatter.dinhDangTien(hoaDon.getKhuyenMai()),
				h[3]);
		pKhuyenMai.setAlignment(Element.ALIGN_RIGHT); // align the paragraph to the right
		pKhuyenMai.setIndentationRight(50);
		document.add(pKhuyenMai);
		Paragraph pThongThanhTien = new Paragraph("Tổng thành tiền: "
				+ VietnameseFormatter.dinhDangTien(hoaDon.getTongTien() - hoaDon.getKhuyenMai()), h[3]);
		pThongThanhTien.setAlignment(Element.ALIGN_RIGHT); // align the paragraph to the right
		pThongThanhTien.setIndentationRight(50);
		document.add(pThongThanhTien);
		Paragraph pTienKhachDua = new Paragraph(
				"Tiền khách đưa: " + VietnameseFormatter.dinhDangTien(hoaDon.getTienKhachDua()), h[3]);
		pTienKhachDua.setAlignment(Element.ALIGN_RIGHT); // align the paragraph to the right
		pTienKhachDua.setIndentationRight(50);
		document.add(pTienKhachDua);
		Paragraph pTienThoi = new Paragraph("Tiền thối: " + VietnameseFormatter
				.dinhDangTien(hoaDon.getTienKhachDua() - hoaDon.getTongTien() + hoaDon.getKhuyenMai()), h[3]);
		pTienThoi.setAlignment(Element.ALIGN_RIGHT); // align the paragraph to the right
		pTienThoi.setIndentationRight(50);
		document.add(pTienThoi);
	}

	private void veLogo() throws MalformedURLException, IOException, DocumentException {
		Image image = Image.getInstance("src/main/resources/images/icons/applogo.png");
		image.scaleAbsolute(50, 50); // Resize the image
		float x = document.getPageSize().getWidth() / 2;
		float y = document.getPageSize().getHeight() / 2;
//        System.out.println("x= " + x + " y= " + y);
		image.setAbsolutePosition(x + 200, y + 320);
		document.add(image);
	}
}
