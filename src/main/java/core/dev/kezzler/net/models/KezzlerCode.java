package core.dev.kezzler.net.models;

import com.google.common.base.Objects;

/**
 * Created by ipodoliak on 19/02/17.
 */
public class KezzlerCode {

    private String name;
    private int size;
    private CodeType codeType;
    private CodeCase codeCase;
    private int codeLength;
    private String id;


    public KezzlerCode(String name, int size) {
        this.name = name;
        this.size = size;
    }


    public KezzlerCode(String id , String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }


    public KezzlerCode(String name, int size, CodeType codeType, CodeCase codeCase, int codeLength) {
        this.name = name;
        this.size = size;
        this.codeType = codeType;
        this.codeCase = codeCase;
        this.codeLength = codeLength;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public CodeCase getCodeCase() {
        return codeCase;
    }

    public int getCodeLength() {
        return codeLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KezzlerCode that = (KezzlerCode) o;
        return size == that.size &&
                Objects.equal(name, that.name);
    }

    @Override
    public String toString() {
        return "KezzlerCode{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, size);
    }

    public enum CodeType {
        Alphanumeric,
        Numeric

    }

    public enum CodeCase {
        Upper_Case,
        Lower_Case

    }
}
