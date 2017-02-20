package core.dev.kezzler.net.models;

import com.google.common.base.Objects;

/**
 * Created by ipodoliak on 19/02/17.
 */
public class KezzlerSection {



    private String id;
    private int startIndex;
    private int endIndex;
    private int size;

    private boolean isEnabled;
    private String maxLimit;


    private KezzlerProduct product;

    public KezzlerSection(String id, int startIndex, int endIndex, int size, boolean isEnabled, String maxLimit, KezzlerProduct product) {
        this.id = id;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.size = size;
        this.isEnabled = isEnabled;
        this.maxLimit = maxLimit;
        this.product = product;
    }

    public KezzlerSection( int startIndex, int endIndex,  boolean isEnabled, String maxLimit, KezzlerProduct product) {

        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.size = endIndex-startIndex+1;
        this.isEnabled = isEnabled;
        this.maxLimit = maxLimit;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KezzlerSection section = (KezzlerSection) o;
        return startIndex == section.startIndex &&
                endIndex == section.endIndex &&
                size == section.size &&
                isEnabled == section.isEnabled &&
                Objects.equal(maxLimit, section.maxLimit) &&
                Objects.equal(product, section.product);
    }

    @Override
    public String toString() {
        return "KezzlerSection{" +
                "id='" + id + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", size=" + size +
                ", isEnabled=" + isEnabled +
                ", maxLimit='" + maxLimit + '\'' +
                ", product=" + product +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(startIndex, endIndex, size, isEnabled, maxLimit, product);
    }
}
