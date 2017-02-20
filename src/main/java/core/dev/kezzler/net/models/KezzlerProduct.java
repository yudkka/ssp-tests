package core.dev.kezzler.net.models;

import com.google.common.base.Objects;

/**
 * Created by ipodoliak on 19/02/17.
 */
public class KezzlerProduct {

    private String productName;
    private String metadata;

    public KezzlerProduct(String productName, String metadata) {
        this.productName = productName;
        this.metadata = metadata;
    }

    public KezzlerProduct(String productName) {
        this.productName = productName;
    }


    public String getProductName() {
        return productName;
    }

    public String getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KezzlerProduct product = (KezzlerProduct) o;
        return Objects.equal(productName, product.productName) &&
                Objects.equal(metadata, product.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productName, metadata);
    }
}
