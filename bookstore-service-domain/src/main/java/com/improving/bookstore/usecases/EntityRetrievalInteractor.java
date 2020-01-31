package com.improving.bookstore.usecases;

import java.util.List;

public interface EntityRetrievalInteractor<T> {

    List<T> invoke();

}
