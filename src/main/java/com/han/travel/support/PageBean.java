package com.han.travel.support;

public class PageBean
{
    private int currPage;//当前页数
    private int pageSize;//每页显示记录数
    private int tolCount;//总记录数
    private int tolPage;//总页数

    public PageBean(int currPage,int pageSize,int tolCount)
    {
        this.currPage=currPage;
        this.pageSize=pageSize;
        this.tolCount=tolCount;
        if(tolCount%pageSize==0)
        {
            this.tolPage=this.tolCount/this.pageSize;
        }
        else
        {
            this.tolPage=this.tolCount/this.pageSize+1;
        }
        if(this.currPage<0)
        {
            this.currPage=1;
        }
        if(this.currPage>=this.tolPage+1)
        {
            this.currPage=this.tolPage;
        }
    }


    public int getCurrPage()
    {
        return currPage;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public int getTolCount()
    {
        return tolCount;
    }

    public int getTolPage()
    {
        return tolPage;
    }
}
