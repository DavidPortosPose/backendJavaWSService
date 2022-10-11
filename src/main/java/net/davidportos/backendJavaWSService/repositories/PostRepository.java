package net.davidportos.backendJavaWSService.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.davidportos.backendJavaWSService.entities.SnippetPostEntity;

@Repository
public interface PostRepository extends PagingAndSortingRepository<SnippetPostEntity, Long> {
    List<SnippetPostEntity> getByUserIdOrderByCreatedDateDesc(long userId);

    @Query(value = "SELECT * FROM posts p WHERE p.exposure_id = :exposure AND p.expiration_date > :now ORDER BY created_date DESC LIMIT 20", nativeQuery = true)
    List<SnippetPostEntity> getLastPublicPosts(@Param("exposure") long exposure_id, @Param("now") Date now);

    SnippetPostEntity findByPostId(String postId);
}
