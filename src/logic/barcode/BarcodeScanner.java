package logic.barcode;

import logic.bean.BarcodeBean;

// @author Danilo D'Amico

public class BarcodeScanner {

    private BarcodeScanner() {
    }

    // classe dummy

    public static BarcodeBean getBarcode() {
        BarcodeBean bean = new BarcodeBean();
        bean.setBarcode("barcode Dummy");
        return bean;
    }

}
