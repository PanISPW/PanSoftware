package logic.bean;

import logic.exception.InvalidDataException;
import logic.util.DataValidation;

// @author Danilo D'Amico

public class BarcodeBean {

    private String barcode = "";
    private int id = -1;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {

        this.barcode = barcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws InvalidDataException {
        if (DataValidation.isNatural(id))
            this.id = id;
        else
            throw new InvalidDataException("id must be a natural number");
    }

}
