package com.godtiner.api.domain.myroutines.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface RevisionRepository<T, ID, N extends Number & Comparable<N>> extends Repository<T, ID> {

    Optional<Revision<N, T>> findLastChangeRevision(ID var1);

    Revisions<N, T> findRevisions(ID var1);

    Page<Revision<N, T>> findRevisions(ID var1, Pageable var2);

    Optional<Revision<N, T>> findRevision(ID var1, N var2);
}


