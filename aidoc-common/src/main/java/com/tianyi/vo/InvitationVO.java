package me.aidoc.appserver.vo;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/8/23.
 */
public class InvitationVO {
    private Integer page;
    private Integer size;
    private String invitationCode;
    private Integer status;
    private Integer channel;
    private Integer inviNumOrder;
    private Long id;
    private Long userId;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getInviNumOrder() {
        return inviNumOrder;
    }

    public void setInviNumOrder(Integer inviNumOrder) {
        this.inviNumOrder = inviNumOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
