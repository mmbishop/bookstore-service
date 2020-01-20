package com.improving.bookstore.usecases;

import java.util.List;

public interface EntityRetrievalUseCase<T> {

    List<T> invoke();

}
