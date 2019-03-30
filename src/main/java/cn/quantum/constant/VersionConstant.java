package cn.quantum.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app-control")
@Data
public class VersionConstant {
    private String version;
    private String download;
    private String appName;
    private String apkName;
    private String devVersion;
    private String dicription;
    private String ifForceUpdate;
}
