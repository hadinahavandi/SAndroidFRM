package ir.sweetsoft.orderapp.ui.product;

import ir.sweetsoft.orderapp.Model.Product;

public class ProductStatus {
    public ProductStatus(Product theProduct) {
        this.theProduct = theProduct;
    }

    private Product theProduct;
    public String getStatusClass(){
        if(theProduct.getIsActive()){
            if(theProduct.Status==null || theProduct.Status==0)
                return "productname";
            else if(theProduct.Status==1)
                return "status-one-productname";
            else if(theProduct.Status==2)
                return "status-two-productname";
            else if(theProduct.Status==3)
                return "status-three-productname";
        }
        else
            return "inactiveproductname";
        return "productname";

    }
    public String getStatusChar(){
        String theChar="";
        if(theProduct.getIsActive()){
            if(theProduct.Status==null || theProduct.Status==0)
                theChar= "";
            else if(theProduct.Status==1)
                theChar=  "--";
            else if(theProduct.Status==2)
                theChar=  "++";
            else if(theProduct.Status==3)
                theChar=  "##";
        }
        else
            theChar=  "**";
        return theChar;

    }

}
