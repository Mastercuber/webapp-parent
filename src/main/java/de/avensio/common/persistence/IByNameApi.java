package de.avensio.common.persistence;

public interface IByNameApi<T extends IWithName> {
    T findByName(final String name);
}
