package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.model.Dan;


public interface DanDao extends CrudRepository<Dan,Integer>, JpaSpecificationExecutor<Dan>,PagingAndSortingRepository<Dan, Integer> {

	@Modifying
	@Query(nativeQuery = true,value = "SELECT * from dan WHERE borrow_name=?1 or lend_name =?1")
	List<Dan> findByBalname(String Balname);

	@Modifying  
	@Query(nativeQuery = true,value = "SELECT * FROM dan WHERE (borrow_name=?1 AND dan_state=?2) OR (lend_name =?1 AND dan_state=?2)")
	List<Dan> findByState(String d_balname, String d_state);
	
	@Modifying
	@Query(nativeQuery = true,value = "SELECT * from dan WHERE dan_id=?1")
	List<Dan> findByDanID(String danid);

}
