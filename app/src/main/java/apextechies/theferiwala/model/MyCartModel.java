package apextechies.theferiwala.model;

/**
 * Created by Shankar on 12/21/2017.
 */

public class MyCartModel {

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String cart_id,productId,productName,productDetail,discountPrice,specification,detail,cartCount,userId,image;

    public MyCartModel(String cart_id, String productId, String productName, String productDetail, String discountPrice, String specification,
                       String detail, String cartCount, String userId, String image)
    {
        this.cart_id = cart_id;
        this.productId = productId;
        this.productName = productName;
        this.productDetail = productDetail;
        this.discountPrice = discountPrice;
        this.specification = specification;
        this.detail = detail;
        this.cartCount = cartCount;
        this.userId = userId;
        this.image = image;
    }
}
