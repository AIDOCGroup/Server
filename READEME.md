1.项目描述：
    天医-链上的AI超能医生-AI Doctor on the Blockchain,天医从医疗数据端切入，研发的AI超能医生形成天医
分布式「大脑」结合智能硬件、物联网等设备，构建智能医疗全产业链生态,把生命体徵数据写入区块链克隆出比特
数字人,让每个人都拥有一个全天候守护的私人AI超能医生！

2.项目说明：
    
    base-*模块为系统基础模块，主要为上层应用提供基本的支持,包括以下模块：

    base-framework:提供基本的框架服务
    base-authority:提供权限相关服务，包括认证，授权，OAUTH2.0 协议支持等。
    base-gateway：提供系统网关服务，是系统的唯一入口,主要为后期微服务架构提供路由功能，待实现。。
	base-scheduler：提供任务调度服务和跑批服务

	aidoc-*模块为系统业务模块,包括以下模块：

    aidoc-shop：商城
    aidoc-common：通用
    aidoc-server：后台服务端
    aidoc-api：提供API接口服务