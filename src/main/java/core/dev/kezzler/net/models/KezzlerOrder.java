package core.dev.kezzler.net.models;

import com.google.common.base.Objects;

/**
 * Created by ipodoliak on 19/02/17.
 */
public class KezzlerOrder {

    public KezzlerOrder(String name, boolean sectioned) {
        this.name = name;
        this.sectioned = sectioned;
    }

    private String name;
    private boolean sectioned;
    private String id;
    private boolean locked;
    private KezzlerProduct product;

    public KezzlerOrder( String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public KezzlerOrder(String id, String name, KezzlerProduct product, boolean locked) {
        this.name = name;
        this.id = id;
        this.locked = locked;
        this.product = product;
    }

    public KezzlerOrder(String id, String name,  boolean sectioned, KezzlerProduct product, boolean locked) {
        this.name = name;
        this.sectioned = sectioned;
        this.id = id;
        this.locked = locked;
        this.product = product;
    }

    public void setProduct(KezzlerProduct product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KezzlerOrder that = (KezzlerOrder) o;
        return sectioned == that.sectioned &&
                locked == that.locked &&
                Objects.equal(name, that.name) &&
                Objects.equal(id, that.id) &&
                Objects.equal(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, sectioned, id, locked, product);
    }

    public void setSectioned(boolean sectioned) {
        this.sectioned = sectioned;
    }

    @Override
    public String toString() {
        return "KezzlerOrder{" +
                "name='" + name + '\'' +
                ", sectioned=" + sectioned +
                ", id='" + id + '\'' +
                ", locked=" + locked +
                ", product=" + product +
                '}';
    }

    public boolean isLocked() {
        return locked;
    }
}
