package com.k.community.entity;

import lombok.Data;

/**
 * @ClassName: Page
 * @Description: 封装分页相关信息
 * @Author 77166
 * @Date 2021/3/26
 */
@Data
public class Page {
    /**
     * 当前页码
     */
    private int current = 1;
    /**
     * 显示上限
     */
    private int limit = 10;
    /**
     * 数据总数(计算总页数)
     */
    private int rows;
    /**
     * 查询路径(复用分页链接)
     */
    private String path;

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    /**
     * 获取当前页起始行
     *
     * @return
     */
    public int getOffset() {
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotal() {
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    /**
     * 获取起始页码
     * @return
     */
    public int getFrom() {
        int front = current - 2;
        return front < 1 ? 1 : front;
    }

    /**
     * 获取终止页码
     * @return
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
