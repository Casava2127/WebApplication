package ra.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class  DNSResolver {
    public static void main(String[] args) {
        // Tên miền cần phân giải
        String domain = "www.example.com";

        System.out.println("==== VÒNG ĐỜI TRUY VẤN DNS ====");
        System.out.println("Tên miền cần phân giải: " + domain);

        try {
            // Bước 1: Kiểm tra cache DNS cục bộ
            System.out.println("\nBước 1: Kiểm tra cache DNS cục bộ...");
            InetAddress cachedAddress = getFromCache(domain);
            if (cachedAddress != null) {
                System.out.println(">> Tìm thấy trong cache: " + cachedAddress.getHostAddress());
            } else {
                System.out.println(">> Không tìm thấy trong cache. Tiếp tục truy vấn.");

                // Bước 2: Gửi yêu cầu đến máy chủ DNS cục bộ
                System.out.println("\nBước 2: Gửi yêu cầu đến máy chủ DNS cục bộ...");
                System.out.println(">> Máy chủ DNS cục bộ nhận yêu cầu và kiểm tra cache của nó.");

                // Bước 3: Truy vấn đến máy chủ gốc (Root DNS Server)
                System.out.println("\nBước 3: Máy chủ DNS cục bộ gửi truy vấn đến máy chủ gốc...");
                System.out.println(">> Máy chủ gốc trả về địa chỉ của máy chủ TLD DNS (ví dụ: .com).");

                // Bước 4: Truy vấn đến máy chủ TLD DNS
                System.out.println("\nBước 4: Máy chủ DNS cục bộ gửi truy vấn đến máy chủ TLD DNS...");
                System.out.println(">> Máy chủ TLD trả về địa chỉ của máy chủ tên miền chịu trách nhiệm (Authoritative Name Server).");

                // Bước 5: Truy vấn đến máy chủ tên miền (Authoritative Name Server)
                System.out.println("\nBước 5: Máy chủ DNS cục bộ gửi truy vấn đến máy chủ tên miền...");
                System.out.println(">> Máy chủ tên miền trả về địa chỉ IP chính xác cho tên miền được yêu cầu.");

                // Bước 6: Trả kết quả phân giải
                System.out.println("\nBước 6: Máy chủ DNS cục bộ trả về kết quả cho trình duyệt...");
                InetAddress resolvedAddress = InetAddress.getByName(domain);
                System.out.println(">> Đã phân giải thành công:");
                System.out.println("   - Tên miền: " + resolvedAddress.getHostName());
                System.out.println("   - Địa chỉ IP: " + resolvedAddress.getHostAddress());
            }

        } catch (UnknownHostException e) {
            System.err.println("\n>> Lỗi: Không thể phân giải tên miền. Vui lòng kiểm tra lại!");
        }
    }

    /**
     * Mô phỏng kiểm tra DNS Cache (tự động thực hiện bởi JVM, đây chỉ là minh họa).
     * @param domain Tên miền cần kiểm tra.
     * @return Địa chỉ IP nếu có trong cache, null nếu không có.
     */
    private static InetAddress getFromCache(String domain) {
        // Giả lập cache (bạn có thể thay đổi để thử nghiệm)
        if ("www.example.com".equals(domain)) {
            try {
                return InetAddress.getByName("93.184.216.34"); // Ví dụ IP từ cache
            } catch (UnknownHostException e) {
                return null;
            }
        }
        return null; // Không có trong cache
    }
}
