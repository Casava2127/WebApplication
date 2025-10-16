package ra.crud.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
// Import lớp `AbstractAnnotationConfigDispatcherServletInitializer` để cấu hình DispatcherServlet bằng Java config.

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    // Lớp `WebConfig` mở rộng từ `AbstractAnnotationConfigDispatcherServletInitializer`
    // để thiết lập cấu hình cho ứng dụng Spring MVC.

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
        // Trả về `null` hoặc mảng rỗng vì không có cấu hình root application context.
        // Đây là nơi cấu hình các lớp Service, Repository nếu cần chia cấu hình.
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
        // Cung cấp cấu hình application context cho DispatcherServlet, trong trường hợp này là lớp `AppConfig`.
        // Lớp `AppConfig` đã cấu hình các bean liên quan đến Spring MVC.
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
        // Mappings xác định tất cả các request bắt đầu từ `/` sẽ được xử lý bởi DispatcherServlet.
        // Điều này giúp Spring MVC xử lý toàn bộ request của ứng dụng.
    }
}
