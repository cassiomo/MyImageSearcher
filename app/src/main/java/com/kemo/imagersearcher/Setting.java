package com.kemo.imagersearcher;

import java.io.Serializable;

/**
 * Created by kemo on 9/20/14.
 */
public class Setting implements Serializable {


    private static final long serialVersionUID = 9189911000284621403L;

    // set to default values;
    public String imageSize = "small";
    public String colorFilter = "blue";
    public String imageType = "faces";
    public String siteFilter = "yahoo.com";
}
