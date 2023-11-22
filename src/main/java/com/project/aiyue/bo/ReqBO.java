package com.project.aiyue.bo;

import lombok.Data;

/**
 *  *
 *  * @author lianggenhao
 *  * @date 2023/11/22 20:31
 *  
 */
public class ReqBO {
    private int pageNum;
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
