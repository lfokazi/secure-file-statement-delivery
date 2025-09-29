package com.lfokazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<TEntity> extends JpaRepository<TEntity, Long>, JpaSpecificationExecutor<TEntity> {
}
