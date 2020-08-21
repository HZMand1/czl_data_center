package cn.paohe.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "router_config")
public class RouterConfig {

    // 接口本系统内id
    @Id
    @Column(name = "ID")
    private String id;

    // 接口名称
    @Column(name = "INTERFACE_NAME")
    private String interfaceName;

    // 上下文名称
    @Column(name = "CONTEXT_NAME")
    private String contextName;

    // 描述
    @Column(name = "DESCRIPTION")
    private String description;

    // 映射路径
    @Column(name = "MAPPING_PATH")
    private String mappingPath;

    // 接口状态
    @Column(name = "STATUS")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMappingPath() {
        return mappingPath;
    }

    public void setMappingPath(String mappingPath) {
        this.mappingPath = mappingPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
