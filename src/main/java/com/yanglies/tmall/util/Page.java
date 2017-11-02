package com.yanglies.tmall.util;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/2 10:57.
 * @ProjectName tmall_ssm
 */
//分页类
public class Page {
    private int start;  //从第几条数据开始分页
    private int count;  //每页显示多少条数据
    private int total;      //数据表中记录数
    private String param;   //暂时不知道干嘛用的 (#^.^#)

    public static final int defaultCount = 5;   //默认每页显示五条数据

    public Page() {
        count = defaultCount;
    }

    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
    }

    //根据total以及count,计算出一共有多少页
    public int getTotalPage(){
        int totalPage;
        if( 0 == total % count){
            totalPage = total / count;
        }else {
            totalPage = total / count + 1;
        }
        if (0 == totalPage){
            totalPage = 1;
        }
        return totalPage;
    }
    //计算最后页的数值
    public int getLast(){
        int last;
        if(0 == total % count){
            last = total - count;
        }else{
            last = total - total % count;
        }
        last = last < 0 ? 0:last;
        return last;
    }

    //计算是否有前一页
    public boolean isHasPreviouse(){
        if(start == 0){
            return false;
        }
        return true;
    }

    //计算是否有后一页
    public boolean isHasNext(){
        if(start == getLast()){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Page [start=" + start
                + ", count=" + count + ", total=" + total
                + ", getStart()=" + getStart()
                + ", getCount()=" + getCount()
                + ", isHasPreviouse()=" + isHasPreviouse()
                + ", isHasNext()=" + isHasNext()
                + ", getTotalPage()=" + getTotalPage()
                + ", getLast()=" + getLast() + "]";
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
