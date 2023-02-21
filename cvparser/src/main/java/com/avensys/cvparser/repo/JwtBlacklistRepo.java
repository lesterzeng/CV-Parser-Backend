package com.avensys.cvparser.repo;

import org.springframework.data.repository.CrudRepository;

import com.avensys.cvparser.entity.JwtBlacklist;

public interface JwtBlacklistRepo extends CrudRepository<JwtBlacklist, Long>{
	
	public boolean existsByJwt(String jwt);

}
