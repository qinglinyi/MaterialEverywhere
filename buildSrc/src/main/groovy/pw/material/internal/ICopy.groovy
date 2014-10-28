package pw.material.internal

public interface ICopy<T extends ICopy<T>> {
    public T copy(MaterialProjectState state);
}
