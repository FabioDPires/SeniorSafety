package com.example.seniorsafety.models;

import java.io.Serializable;

public class SearchResponse implements Serializable {

    private String next_page_token;

    private String[] html_attributions;

    private Result[] results;

    private String status;

    public String getNext_page_token ()
    {
        return next_page_token;
    }

    public void setNext_page_token (String next_page_token)
    {
        this.next_page_token = next_page_token;
    }

    public String[] getHtml_attributions ()
    {
        return html_attributions;
    }

    public void setHtml_attributions (String[] html_attributions)
    {
        this.html_attributions = html_attributions;
    }

    public Result[] getResults ()
    {
        return results;
    }

    public void setResults (Result[] results)
    {
        this.results = results;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [next_page_token = "+next_page_token+", html_attributions = "+html_attributions+", results = "+results+", status = "+status+"]";
    }
}
