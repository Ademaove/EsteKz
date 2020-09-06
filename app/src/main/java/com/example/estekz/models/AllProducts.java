package com.example.estekz.models;

import java.time.OffsetDateTime;
import java.util.List;

public class AllProducts {
    private long id;
    private String name;
    private String slug;
    private String permalink;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateCreatedGmt;
    private OffsetDateTime dateModified;
    private OffsetDateTime dateModifiedGmt;
    private String type;
    private String status;
    private boolean featured;
    private String catalogVisibility;
    private String description;
    private String shortDescription;
    private String sku;
    private String price;
    private String regularPrice;
    private String salePrice;
    private Object dateOnSaleFrom;
    private Object dateOnSaleFromGmt;
    private Object dateOnSaleTo;
    private Object dateOnSaleToGmt;
    private String priceHTML;
    private boolean onSale;
    private boolean purchasable;
    private long totalSales;
    private boolean virtual;
    private boolean downloadable;
    private List<Object> downloads;
    private long downloadLimit;
    private long downloadExpiry;
    private String externalURL;
    private String buttonText;
    private String taxStatus;
    private String taxClass;
    private boolean manageStock;
    private Object stockQuantity;
    private String stockStatus;
    private String backorders;
    private boolean backordersAllowed;
    private boolean backordered;
    private boolean soldIndividually;
    private String weight;
    private Dimensions dimensions;
    private boolean shippingRequired;
    private boolean shippingTaxable;
    private String shippingClass;
    private long shippingClassID;
    private boolean reviewsAllowed;
    private String averageRating;
    private long ratingCount;
    private List<Object> relatedIDS;
    private List<Object> upsellIDS;
    private List<Object> crossSellIDS;
    private long parentID;
    private String purchaseNote;
    private List<Category> categories;
    private List<Category> tags;
    private List<Image> images;
    private List<Object> attributes;
    private List<Object> defaultAttributes;
    private List<Object> variations;
    private List<Object> groupedProducts;
    private long menuOrder;
    private List<MetaDatum> metaData;
    private String yoastHead;
    private String vendor;
    private String storeName;
    private Links links;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }
}
