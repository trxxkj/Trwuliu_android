package cn.trxxkj.trwuliu.driver.bean;

/**
 *  我司机  实体类  请求
 */
public class MyDrivers {

    private String pageNo;
    private String memberId;
    private String search;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}
