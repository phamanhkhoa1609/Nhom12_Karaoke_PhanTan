import jakarta.persistence.TypedQuery;
import net.datafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

import entities.*;
import entities.enums.*;

public class Run {
    public static void main(String[] args) {
        Faker faker = new Faker();
        Random random = new Random();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA ORM MariaDB");
        EntityManager em = emf.createEntityManager();

        Set<String> uniquePhoneNumbers = new HashSet<>();

        try {
            em.getTransaction().begin();

            // Tạo dữ liệu giả cho KhachHang và TaiKhoan
            for (int i = 0; i < 10; i++) {
                String uniquePhoneNumber;
                do {
                    uniquePhoneNumber = faker.phoneNumber().cellPhone();
                } while (!uniquePhoneNumbers.add(uniquePhoneNumber));

                KhachHang khachHang = new KhachHang();
                khachHang.setHoTen(faker.name().fullName());
                khachHang.setSdt(uniquePhoneNumber);
                khachHang.setGioiTinh(GioiTinh.values()[faker.number().numberBetween(0, GioiTinh.values().length)]);
                khachHang.setHoiVien(HoiVien.values()[faker.number().numberBetween(0, HoiVien.values().length)]);

                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setSdt(uniquePhoneNumber);
                taiKhoan.setMatKhau(faker.internet().password());
                khachHang.setTaiKhoan(taiKhoan);

                em.persist(taiKhoan);
                em.persist(khachHang);
            }

            // Tạo dữ liệu giả cho NhanVien và TaiKhoan
            for (int i = 0; i < 10; i++) {
                String uniquePhoneNumber;
                do {
                    uniquePhoneNumber = faker.phoneNumber().cellPhone();
                } while (!uniquePhoneNumbers.add(uniquePhoneNumber));

                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setSdt(uniquePhoneNumber);
                taiKhoan.setMatKhau(faker.internet().password());
                em.persist(taiKhoan);

                LocalDate minDate = LocalDate.of(1980, 1, 1);
                LocalDate maxDate = LocalDate.of(2005, 1, 1);
                LocalDate randomBirthday = minDate.plusDays(faker.number().numberBetween(0, (int) (maxDate.toEpochDay() - minDate.toEpochDay())));

                NhanVien nhanVien = new NhanVien();
                nhanVien.setHoTen(faker.name().fullName());
                nhanVien.setSdt(uniquePhoneNumber);
                nhanVien.setGioiTinh(GioiTinh.values()[faker.number().numberBetween(0, GioiTinh.values().length)]);
                nhanVien.setNgaySinh(randomBirthday);
                nhanVien.setChucVu(ChucVu.values()[faker.number().numberBetween(0, ChucVu.values().length)]);
                nhanVien.setTrangThai(TrangThaiNhanVien.values()[faker.number().numberBetween(0, TrangThaiNhanVien.values().length)]);
                nhanVien.setPublicID(faker.internet().uuid());
                nhanVien.setCccd(faker.idNumber().valid());
                nhanVien.setDiaChi(faker.address().fullAddress());
                nhanVien.setTaiKhoan(taiKhoan);

                em.persist(nhanVien);
            }

            // Tạo dữ liệu giả cho Phong
            for (int i = 0; i < 10; i++) {
                Phong phong = new Phong();
                phong.setTenPhong("Phòng " + (i + 1));
                phong.setGiaPhong(faker.number().numberBetween(5, 20) * 10000);
                phong.setMoTa(faker.lorem().sentence(10));
                phong.setPublicID(faker.internet().uuid());
                phong.setLoaiPhong(LoaiPhong.values()[faker.number().numberBetween(0, LoaiPhong.values().length)]);
                phong.setTrangThai(TrangThaiPhong.values()[faker.number().numberBetween(0, TrangThaiPhong.values().length)]);

                em.persist(phong);
            }

            // Tạo dữ liệu giả cho bảng DichVu
            for (int i = 0; i < 20; i++) {
                DichVu dichVu = new DichVu();
                dichVu.setTenDV(faker.commerce().productName());
                dichVu.setMoTa(faker.lorem().sentence(10));
                dichVu.setSoLuong(random.nextInt(101));
                dichVu.setGiaDV(faker.number().numberBetween(1, 10) * 10000);
                dichVu.setPublicID(faker.internet().uuid());
                dichVu.setLoaiDV(LoaiDichVu.values()[random.nextInt(LoaiDichVu.values().length)]);
                dichVu.setTrangThai(TrangThaiDichVu.values()[random.nextInt(TrangThaiDichVu.values().length)]);

                em.persist(dichVu);
            }

            // Tạo dữ liệu giả cho HoaDon, ChiTietHoaDonPhong và ChiTietHoaDonDichVu
            for (int i = 0; i < 10; i++) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setThoiGianLap(LocalDateTime.now());
                hoaDon.setThoiHan(hoaDon.getThoiGianLap().plusDays(7));
                hoaDon.setTrangThai(TrangThaiHoaDon.values()[faker.number().numberBetween(0, TrangThaiHoaDon.values().length)]);

                /// Lấy KhachHang ngẫu nhiên
                String sqlKhachHang = "SELECT * FROM khachhang ORDER BY RAND() LIMIT 1";
                KhachHang khachHang = (KhachHang) em.createNativeQuery(sqlKhachHang, KhachHang.class).getSingleResult();

                // Lấy NhanVien ngẫu nhiên theo chức vụ
                String sqlNhanVienTiepTan = "SELECT * FROM nhanvien WHERE chucvu = 'TIẾPTÂN' ORDER BY RAND() LIMIT 1";
                NhanVien tiepTan = (NhanVien) em.createNativeQuery(sqlNhanVienTiepTan, NhanVien.class).getSingleResult();

                String sqlNhanVienPhucVu = "SELECT * FROM nhanvien WHERE chucvu = 'PHỤCVỤ' ORDER BY RAND() LIMIT 1";
                NhanVien phucVu = (NhanVien) em.createNativeQuery(sqlNhanVienPhucVu, NhanVien.class).getSingleResult();

                hoaDon.setKhachHang(khachHang);
                hoaDon.setTiepTan(tiepTan);
                hoaDon.setPhucVu(phucVu);

                // Lưu hóa đơn trước khi thêm chi tiết
                em.persist(hoaDon);

                // **Tạo ChiTietHoaDonPhong**
                String sqlPhong = "SELECT * FROM phong ORDER BY RAND() LIMIT 1";
                Phong phong = (Phong) em.createNativeQuery(sqlPhong, Phong.class).getSingleResult();

                if (em.contains(phong)) {
                    phong = em.merge(phong); // Đồng bộ với Hibernate nếu cần
                }

                ChiTietHoaDonPhong chiTietPhong = new ChiTietHoaDonPhong();
                chiTietPhong.setThoiGianBatDau(LocalDateTime.now().plusMinutes(faker.number().numberBetween(0, 100)));
                chiTietPhong.setThoiGianKetThuc(LocalTime.from(chiTietPhong.getThoiGianBatDau().plusHours(faker.number().numberBetween(1, 5))));
                chiTietPhong.setDonGia(phong.getGiaPhong());
                chiTietPhong.setPhong(phong);
                chiTietPhong.setHoaDon(hoaDon);

                hoaDon.getDsCTHDP().add(chiTietPhong);
                em.persist(chiTietPhong);

                // **Tạo ChiTietHoaDonDichVu**
                for (int j = 0; j < faker.number().numberBetween(1, 4); j++) { // Mỗi hóa đơn có từ 1-3 dịch vụ
                    String sqlDichVu = "SELECT * FROM dichvu ORDER BY RAND() LIMIT 1";
                    DichVu dichVu = (DichVu) em.createNativeQuery(sqlDichVu, DichVu.class).getSingleResult();

                    if (dichVu == null) continue;

                    ChiTietHoaDonDichVu chiTietDichVu = new ChiTietHoaDonDichVu();
                    chiTietDichVu.setDonGia(dichVu.getGiaDV());
                    chiTietDichVu.setSoLuong(faker.number().numberBetween(1, 10));
                    chiTietDichVu.setHoaDon(hoaDon);
                    chiTietDichVu.setDichVu(dichVu);

                    em.persist(chiTietDichVu);
                    hoaDon.getDsCTHDDV().add(chiTietDichVu);
                }

                em.merge(hoaDon);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
