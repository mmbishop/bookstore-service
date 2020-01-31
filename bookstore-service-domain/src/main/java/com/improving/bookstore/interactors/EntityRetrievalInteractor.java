package com.improving.bookstore.interactors;

import java.util.List;

public interface EntityRetrievalInteractor<T> {

    List<T> invoke();

}
