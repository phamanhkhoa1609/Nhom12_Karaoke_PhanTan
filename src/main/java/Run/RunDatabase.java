package Run;

import dao.DichVuDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.PhongDAO;
import entities.*;
import entities.enums.*;
import jakarta.persistence.EntityManager;
import utils.EntityManagerFactoryUltil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RunDatabase {
	private static EntityManagerFactoryUltil entityManagerFactoryUltil;
	private static EntityManager entityManager;
	public static void main(String[] args) {
		entityManagerFactoryUltil = new EntityManagerFactoryUltil("jpa-mariadb-karaGOke-recreate");
		entityManager = entityManagerFactoryUltil.getEntityManager();
		try {
			luuPhong();
			luuDichVu();
			luuKhachHang();
			luuNhanVien();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		entityManagerFactoryUltil.closeEntityManager();
		entityManagerFactoryUltil.closeEntityManagerFactory();
		
		
	}
	private static void luuPhong() throws Exception {
		ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		Phong phong1 = new Phong();
		dsPhong.add(phong1);
		phong1.setGiaPhong(30000);
		phong1.setPublicID("hnzshkq16l99qunjgfjd");
		phong1.setLoaiPhong(LoaiPhong.CAFE);
		phong1.setMoTa("Phòng Cafe Karaoke: Nơi Hòa Quyện Âm Nhạc và Hương Vị\r\n"
				+ "\r\n"
				+ "Trong bóng đêm, ánh đèn mờ lung linh, phòng cafe karaoke nở hoa. Diện tích phòng không quá rộng, nhưng đủ để chứa đựng những giấc mơ và cảm xúc của những người đến đây. Bức tường được trang trí bằng những hình ảnh nghệ thuật, tạo nên không gian ấm cúng và thú vị.\r\n"
				+ "\r\n"
				+ "Âm thanh là yếu tố quan trọng nhất. Hệ thống loa, ampli và micro được lắp đặt một cách tinh tế, đảm bảo âm thanh trong phòng không bị lọt ra ngoài. Các tấm cách âm được bố trí kỹ lưỡng, giúp âm nhạc trở nên trong trẻo và sống động.\r\n"
				+ "\r\n"
				+ "Ánh sáng cũng đóng vai trò quan trọng. Đèn led màu sắc pha lẫn nhau, tạo ra không gian thần tiên. Những đèn nhấp nháy theo nhịp nhạc, khiến mọi người cảm thấy như đang ở trong một buổi biểu diễn thực sự.\r\n"
				+ "\r\n"
				+ "Nội thất được chọn lựa kỹ càng. Ghế sofa êm ái, bàn uống nước gỗ sáng bóng. Mọi thứ đều hòa quyện với nhau, tạo nên không gian thoải mái và thư thái.\r\n"
				+ "\r\n"
				+ "Diện tích phòng không quá lớn, nhưng đủ để mọi người cùng hát hò, cười đùa và tận hưởng những khoảnh khắc đáng nhớ. Phòng cafe karaoke không chỉ là nơi giải trí, mà còn là nơi để tạo kỷ niệm và kết nối con người.");
		phong1.setTenPhong("Phòng 1");
		phong1.setTrangThai(TrangThaiPhong.TRỐNG);
		
		Phong phong2 = new Phong();
		dsPhong.add(phong2);
		phong2.setGiaPhong(50000);
		phong2.setPublicID("jw3vkzjdoncwq6fm6idp");
		phong2.setLoaiPhong(LoaiPhong.CAFE);
		phong2.setMoTa("Phòng Cafe Karaoke: Nơi Hòa Quyện Âm Nhạc và Hương Vị\r\n"
				+ "\r\n"
				+ "Trong bóng đêm, ánh đèn mờ lung linh, phòng cafe karaoke nở hoa. Diện tích phòng không quá rộng, nhưng đủ để chứa đựng những giấc mơ và cảm xúc của những người đến đây. Bức tường được trang trí bằng những hình ảnh nghệ thuật, tạo nên không gian ấm cúng và thú vị.\r\n"
				+ "\r\n"
				+ "Âm thanh là yếu tố quan trọng nhất. Hệ thống loa, ampli và micro được lắp đặt một cách tinh tế, đảm bảo âm thanh trong phòng không bị lọt ra ngoài. Các tấm cách âm được bố trí kỹ lưỡng, giúp âm nhạc trở nên trong trẻo và sống động.\r\n"
				+ "\r\n"
				+ "Ánh sáng cũng đóng vai trò quan trọng. Đèn led màu sắc pha lẫn nhau, tạo ra không gian thần tiên. Những đèn nhấp nháy theo nhịp nhạc, khiến mọi người cảm thấy như đang ở trong một buổi biểu diễn thực sự.\r\n"
				+ "\r\n"
				+ "Nội thất được chọn lựa kỹ càng. Ghế sofa êm ái, bàn uống nước gỗ sáng bóng. Mọi thứ đều hòa quyện với nhau, tạo nên không gian thoải mái và thư thái.\r\n"
				+ "\r\n"
				+ "Diện tích phòng không quá lớn, nhưng đủ để mọi người cùng hát hò, cười đùa và tận hưởng những khoảnh khắc đáng nhớ. Phòng cafe karaoke không chỉ là nơi giải trí, mà còn là nơi để tạo kỷ niệm và kết nối con người.");
		phong2.setTenPhong("Phòng 2");
		phong2.setTrangThai(TrangThaiPhong.ĐANGBẢOTRÌ);
		
		Phong phong3 = new Phong();
		dsPhong.add(phong3);
		phong3.setGiaPhong(40000);
		phong3.setPublicID("hnzshkq16l99qunjgfjd");
		phong3.setLoaiPhong(LoaiPhong.CAFE);
		phong3.setMoTa("Phòng Cafe Karaoke: Nơi Hòa Quyện Âm Nhạc và Hương Vị\r\n"
				+"\r\n"
				+"Trong bóng đêm, ánh đèn mờ lung linh, phòng cafe karaoke nở hoa. Diện tích phòng không quá rộng, nhưng đủ để chứa đựng những giấc mơ và cảm xúc của những người đến đây. Bức tường được trang trí bằng những hình ảnh nghệ thuật, tạo nên không gian ấm cúng và thú vị.\r\n"
				+"\r\n"
				+"Âm thanh là yếu tố quan trọng nhất. Hệ thống loa, ampli và micro được lắp đặt một cách tinh tế, đảm bảo âm thanh trong phòng không bị lọt ra ngoài. Các tấm cách âm được bố trí kỹ lưỡng, giúp âm nhạc trở nên trong trẻo và sống động.\r\n"
				+"\r\n"
				+"Ánh sáng cũng đóng vai trò quan trọng. Đèn led màu sắc pha lẫn nhau, tạo ra không gian thần tiên. Những đèn nhấp nháy theo nhịp nhạc, khiến mọi người cảm thấy như đang ở trong một buổi biểu diễn thực sự.\r\n"
				+"\r\n"
				+"Nội thất được chọn lựa kỹ càng. Ghế sofa êm ái, bàn uống nước gỗ sáng bóng. Mọi thứ đều hòa quyện với nhau, tạo nên không gian thoải mái và thư thái.\r\n"
				+"\r\n"
				+"Diện tích phòng không quá lớn, nhưng đủ để mọi người cùng hát hò, cười đùa và tận hưởng những khoảnh khắc đáng nhớ. Phòng cafe karaoke không chỉ là nơi giải trí, mà còn là nơi để tạo kỷ niệm và kết nối con người."); phong3.setTenPhong("Phòng 3"); phong3.setTrangThai(TrangThaiPhong.TRỐNG);
				
		Phong phong4 = new Phong();
		dsPhong.add(phong4);
		phong4.setGiaPhong(60000);
		phong4.setPublicID("jn5arduvqraww7bvv2qw");
		phong4.setLoaiPhong(LoaiPhong.HIỆNĐẠI);
		phong4.setMoTa("Phòng Cafe Karaoke: Nơi Hòa Quyện Âm Nhạc và Hương Vị\r\n"
				+"\r\n"
				+"Trong bóng đêm, ánh đèn mờ lung linh, phòng cafe karaoke nở hoa. Diện tích phòng không quá rộng, nhưng đủ để chứa đựng những giấc mơ và cảm xúc của những người đến đây. Bức tường được trang trí bằng những hình ảnh nghệ thuật, tạo nên không gian ấm cúng và thú vị.\r\n"
				+"\r\n"
				+"Âm thanh là yếu tố quan trọng nhất. Hệ thống loa, ampli và micro được lắp đặt một cách tinh tế, đảm bảo âm thanh trong phòng không bị lọt ra ngoài. Các tấm cách âm được bố trí kỹ l");
		phong4.setTenPhong("Phòng 4");
		phong4.setTrangThai(TrangThaiPhong.TRỐNG);
		
		Phong phong5 = new Phong();
		dsPhong.add(phong5);
		phong5.setGiaPhong(30000);
		phong5.setPublicID("usltwm94hjhr3prp0uwe");
		phong5.setLoaiPhong(LoaiPhong.HOÀNGGIA);
		phong5.setMoTa("Phòng Cafe Karaoke: Nơi Hòa Quyện Âm Nhạc và Hương Vị\r\n"
		        + "\r\n"
		        + "Trong bóng đêm, ánh đèn mờ lung linh, phòng cafe karaoke nở hoa. Diện tích phòng không quá rộng, nhưng đủ để chứa đựng những giấc mơ và cảm xúc của những người đến đây. Bức tường được trang trí bằng những hình ảnh nghệ thuật, tạo nên không gian ấm cúng và thú vị.\r\n"
		        + "\r\n"
		        + "Âm thanh là yếu tố quan trọng nhất. Hệ thống loa, ampli và micro được lắp đặt một cách tinh tế, đảm bảo âm thanh trong phòng không bị lọt ra ngoài. Các tấm cách âm được bố trí kỹ lưỡng, giúp âm nhạc trở nên trong trẻo và sống động.\r\n"
		        + "\r\n"
		        + "Ánh sáng cũng đóng vai trò quan trọng. Đèn led màu sắc pha lẫn nhau, tạo ra không gian thần tiên. Những đèn nhấp nháy theo nhịp nhạc, khiến mọi người cảm thấy như đang ở trong một buổi biểu diễn thực sự.\r\n"
		        + "\r\n"
		        + "Nội thất được chọn lựa kỹ càng. Ghế sofa êm ái, bàn uống nước gỗ sáng bóng. Mọi thứ đều hòa quyện với nhau, tạo nên không gian thoải mái và thư thái.\r\n"
		        + "\r\n"
		        + "Diện tích phòng không quá lớn, nhưng đủ để mọi người cùng hát hò, cười đùa và tận hưởng những khoảnh khắc đáng nhớ. Phòng cafe karaoke không chỉ là nơi giải trí, mà còn là nơi để tạo kỷ niệm và kết nối con người.");
		phong5.setTenPhong("Phòng 5");
		phong5.setTrangThai(TrangThaiPhong.TRỐNG);
		
		PhongDAO phongDAO = new PhongDAO(entityManager);
		phongDAO.themNhieu(dsPhong);
	}
	private static void luuDichVu() throws Exception {
		List<DichVu> dsDV = new ArrayList<DichVu>();
		DichVu dichVu1 = new DichVu();
		dsDV.add(dichVu1);
		dichVu1.setGiaDV(12000);
		dichVu1.setLoaiDV(LoaiDichVu.BIA);
		dichVu1.setTenDV("Bia Sài Gòn Special");
		dichVu1.setMoTa("Sản phẩm bia Saigon Special với thành phần 100% malt (không có gạo), được sản xuất trên dây chuyền công nghệ hiện đại tiên tiến bậc nhất khu vực và lên men theo công nghệ truyền thống dài ngày tạo nên một hương vị ngon và độc đáo khác hẳn với các sản phẩm bia khác trên thị trường.Saigon Special là loại bia đặc biệt dành cho người tiêu dùng trẻ trung, năng động và thành công trong cuộc sống.");
		dichVu1.setPublicID("");
		dichVu1.setSoLuong(31);
		dichVu1.setTrangThai(TrangThaiDichVu.KINHDOANH);
		
		DichVu dichVu2 = new DichVu();
		dsDV.add(dichVu2);
		dichVu2.setGiaDV(6000);
		dichVu2.setLoaiDV(LoaiDichVu.BÁNH);
		dichVu2.setTenDV("Snack cua vị sốt chua ngọt Oishi Crab Me gói 8g");
		dichVu2.setMoTa("Snack của giòn ngon, thơm thơm vị sốt chua ngọt kích thích vị giác vô cùng. Snack cua vị sốt chua ngọt Oishi Crab Me gói 8g hấp dẫn, phù hợp vừa xem phim, vừa nghe nhạc vừa nhâm nhi thưởng thức. Snack Oishi tiện lợi, nhỏ gọn, dễ mang theo cho các buổi hoạt động ngoài trời.\r\n");
		dichVu2.setPublicID("flfjoyyxyxldhrcvkrlh");
		dichVu2.setSoLuong(161);
		dichVu2.setTrangThai(TrangThaiDichVu.NGỪNGKINHDOANH);

		DichVu dichVu3 = new DichVu();
		dsDV.add(dichVu3);
		dichVu3.setGiaDV(6000);
		dichVu3.setLoaiDV(LoaiDichVu.NƯỚCNGỌT);
		dichVu3.setTenDV("Lon nước ngọt Pepsi Cola 320ml");
		dichVu3.setMoTa("Nước ngọt Pepsi lần đầu tiên được tạo ra từ nước cacbonat, đường, vani và một chút dầu ăn rất đơn giản để trở thành một loại thức uống giúp bạn dễ tiêu hoá và được mệnh danh là nước uống của Brad do ông Bradham đã tìm ra công thức này vào năm 1886.\r\n"
				+ "\r\n"
				+ "Trong những năm 1980, Pepsi đã chuyển từ đường sang dùng syro ngô, một chất tạo ngọt rẻ hơn. Đồng thời sau đó, công ty cũng thay đổi chất tạo màu caramel trong nước uống của mình để tránh việc bị gắn mác gây ung thư cho người tiêu dùng.\r\n"
				+ "\r\n"
				+ "Pepsi là nhãn hiệu nước giải khát hương Cola có gaz nổi tiếng toàn cầu, được kế thừa nhiều giá trị lịch sử lâu đời. Tại Việt Nam, tự hào là một nhãn hàng đại diện cho tiếng nói của giới trẻ cùng với thông điệp “CỨ TRẺ, CỨ CHẤT, CỨ PEPSI”, chúng tôi luôn tìm cách mang đến lại những trải nghiệm sảng khoái tột đỉnh, khuyến khích người trẻ nắm bắt và tận hưởng từng khoảnh khắc thú vị của cuộc sống.\r\n"
				+ "\r\n"
				+ "Nước Ngọt Có Gaz Pepsi với hương cola hấp dẫn, mang lại cảm giác sảng khoái, giải nhiệt tức thì trong những ngày nóng bức. Dùng trực tiếp, sẽ ngon hơn khi ướp lạnh, hoặc dùng với đá.");
		dichVu3.setPublicID("u6ekt4xjz6u4cbzvqerr");
		dichVu3.setSoLuong(91);
		dichVu3.setTrangThai(TrangThaiDichVu.KINHDOANH);
		
		DichVu dichVu4 = new DichVu();
		dsDV.add(dichVu4);
		dichVu4.setGiaDV(6000);
		dichVu4.setLoaiDV(LoaiDichVu.BÁNH);
		dichVu4.setTenDV("Nước tăng lực vị cà phê Wake Up 247 ít đường chai 330ml");
		dichVu4.setMoTa("Nước tăng lực với các thành phần tự nhiên an toàn cho sức khỏe, bổ sung thêm vitamin và dưỡng chất cần thiết cho cơ thể, nước tăng lực vị cà phê Wake Up 247 ít đường 330ml có mùi vị thơm ngon, sảng khoái cùng hương cà phê thơm dễ chịu. Sản phẩm chính hãng từ thương hiệu nước tăng lực Wake up 247.");
		dichVu4.setPublicID("muvgdw2nrvzjvb1hfoyi");
		dichVu4.setSoLuong(11);
		dichVu4.setTrangThai(TrangThaiDichVu.KINHDOANH);
		
		DichVu dichVu5 = new DichVu();
		dsDV.add(dichVu5);
		dichVu5.setGiaDV(69000);
		dichVu5.setLoaiDV(LoaiDichVu.TRÁICÂY);
		dichVu5.setTenDV("Nho xanh Nam Phi");
		dichVu5.setMoTa("69.000₫ / Túi 500g");
		dichVu5.setPublicID("jamw0p4b1kliay0hcxq5");
		dichVu5.setSoLuong(48);
		dichVu5.setTrangThai(TrangThaiDichVu.KINHDOANH);
		
		DichVuDAO dichVuDAO = new DichVuDAO(entityManager);
		dichVuDAO.themNhieu(dsDV);
	}
	private static void luuKhachHang() throws Exception {
		List<KhachHang> dsKH = new ArrayList<KhachHang>();
		
		KhachHang khachHangA = new KhachHang();
		dsKH.add(khachHangA);
		khachHangA.setGioiTinh(GioiTinh.NAM);
		khachHangA.setHoiVien(HoiVien.BẠC);
		khachHangA.setHoTen("Khách hàng A");
		khachHangA.setSdt("123456789");
		khachHangA.setTaiKhoan(new TaiKhoan("123456789", "123"));
		
		KhachHang khachHangB = new KhachHang();
		dsKH.add(khachHangB);
		khachHangB.setGioiTinh(GioiTinh.NỮ);
		khachHangB.setHoiVien(HoiVien.BẠC);
		khachHangB.setHoTen("Khách hàng B");
		khachHangB.setSdt("1234823762");
		khachHangB.setTaiKhoan(new TaiKhoan("1234823762", "1233"));
		
		KhachHangDAO khachHangDAO  = new KhachHangDAO(entityManager);
		khachHangDAO.themNhieu(dsKH);
	}
	private static void luuNhanVien() throws Exception {
		List<NhanVien> dsNV = new ArrayList<NhanVien>();
		
		NhanVien phucVuA = new NhanVien();
		dsNV.add(phucVuA);
		phucVuA.setGioiTinh(GioiTinh.NAM);
		phucVuA.setHoTen("Phục Vụ A");
		phucVuA.setSdt("2126789");
		phucVuA.setTaiKhoan(new TaiKhoan("2126789", "123"));
		phucVuA.setCccd("845837");
		phucVuA.setChucVu(ChucVu.PHỤCVỤ);
		phucVuA.setDiaChi("aopiufdi90asof");
		phucVuA.setNgaySinh(LocalDate.now());
		phucVuA.setPublicID("abhqiydvekyhaolmcdbm");
		phucVuA.setTrangThai(TrangThaiNhanVien.ĐANGLÀM);

		
		NhanVien phucVuB = new NhanVien();
		dsNV.add(phucVuB);
		phucVuB.setGioiTinh(GioiTinh.NỮ);
		phucVuB.setHoTen("Phục vụ B");
		phucVuB.setSdt("1238232762");
		phucVuB.setTaiKhoan(new TaiKhoan("1238232762", "1233"));
		phucVuB.setCccd("09283437");
		phucVuB.setChucVu(ChucVu.PHỤCVỤ);
		phucVuB.setDiaChi("ao0928359802437piufdi90asof");
		phucVuB.setNgaySinh(LocalDate.now());
		phucVuB.setPublicID("coyc2zvannzhdejgod5w");
		phucVuB.setTrangThai(TrangThaiNhanVien.ĐANGLÀM);

		
		NhanVien tiepTanA = new NhanVien();
		dsNV.add(tiepTanA);
		tiepTanA.setGioiTinh(GioiTinh.NỮ);
		tiepTanA.setHoTen("Tiếp tân A");
		tiepTanA.setSdt("011232322");
		tiepTanA.setTaiKhoan(new TaiKhoan("011232322", "123"));
		tiepTanA.setCccd("022343537");
		tiepTanA.setChucVu(ChucVu.TIẾPTÂN);
		tiepTanA.setDiaChi("aaf8asof");
		tiepTanA.setNgaySinh(LocalDate.now());
		tiepTanA.setPublicID("lpxhgyovm9zsv4yv5etv");
		tiepTanA.setTrangThai(TrangThaiNhanVien.ĐANGLÀM);

		
		NhanVien tiepTanB = new NhanVien();
		dsNV.add(tiepTanB);
		tiepTanB.setGioiTinh(GioiTinh.NỮ);
		tiepTanB.setHoTen("Tiếp tân B");
		tiepTanB.setSdt("0123233762");
		tiepTanB.setTaiKhoan(new TaiKhoan("0123233762", "123"));
		tiepTanB.setCccd("022433537");
		tiepTanB.setChucVu(ChucVu.TIẾPTÂN);
		tiepTanB.setDiaChi("aaf8asof");
		tiepTanB.setNgaySinh(LocalDate.now());
		tiepTanB.setPublicID("lpxhgyovm9zsv4yv5etv");
		tiepTanB.setTrangThai(TrangThaiNhanVien.ĐANGLÀM);

		
		NhanVien quanLy = new NhanVien();
		dsNV.add(quanLy);
		quanLy.setGioiTinh(GioiTinh.NAM);
		quanLy.setHoTen("Khoi Dao");
		quanLy.setSdt("0865100640");
		quanLy.setTaiKhoan(new TaiKhoan("daongocanhkhoi@gmail.com", "123456asd"));
		quanLy.setCccd("078202998003");
		quanLy.setChucVu(ChucVu.QUẢNLÝ);
		quanLy.setDiaChi("Go Vap");
		quanLy.setNgaySinh(LocalDate.now());
		quanLy.setTrangThai(TrangThaiNhanVien.ĐANGLÀM);
		quanLy.setPublicID("lvndxphmvkmt2ygsz6wc");
		
		NhanVienDAO nhanVienDAO  = new NhanVienDAO(entityManager);
		nhanVienDAO.themNhieu(dsNV);
	}
}
