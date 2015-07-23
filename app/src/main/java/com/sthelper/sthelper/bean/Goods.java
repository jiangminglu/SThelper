package com.sthelper.sthelper.bean;

import java.util.List;

/**
 * 商品分类， 比如 面条，而面条里面又有 牛肉面，鸡汤面等， 就是 goodsInfo
 * Created by luffy on 15/7/23.
 */
public class Goods {
    public String cate_name;//食品分类名称
    public List<GoodsInfo> goodsinfo;
}
