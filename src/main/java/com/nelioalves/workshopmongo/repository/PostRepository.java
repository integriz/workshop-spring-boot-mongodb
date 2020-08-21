package com.nelioalves.workshopmongo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.nelioalves.workshopmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<Post> searchTitle(String text);
	
	//Este tipo de ocnsulta gerada automaticamente pelo Spring chama-se query methods, veja mais detalhe no site 
	//https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
	//https://docs.spring.io/spring-data/data-document/docs/current/reference/html/
	
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	/*
	  Abaixo outras formas utilizando Query do MongoDB
	
	Mais detalhes:
	
	https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
	https://docs.spring.io/spring-data/data-document/docs/current/reference/html/
	https://docs.mongodb.com/manual/reference/operator/query/regex/
	
	
	 */
	
	@Query("{ $and: [ { date: {$gte: ?1} }, { date: { $lte: ?2} } , { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, Date minDate, Date maxDate);
}
