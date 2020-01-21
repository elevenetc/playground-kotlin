package utils;

import java.util.Objects;

public class SampleObject {

    protected int id;
    protected String name;
    protected String another;

    public SampleObject(int id, String name, String another) {
        this.id = id;
        this.name = name;
        this.another = another;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SampleObject that = (SampleObject) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}