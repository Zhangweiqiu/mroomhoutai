package com.example.demo.dao;

import java.util.Date;
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
	
	@Modifying
	@Query(nativeQuery = true,value = "delete from dan WHERE dan_id=?1")
	void deleteDan(String danid);
	
	@Modifying
	@Query(nativeQuery = true,value = "SELECT * FROM dan order by borrow_date Desc")
	List<Dan> selectDan();
	
	@Modifying
	@Query(nativeQuery = true,value = "update dan set dan_state='还款中',repay_date = ?1  WHERE dan_id=?2")
	void hk(Date date,String danid);
	
	@Modifying
	@Query(nativeQuery = true,value = "update dan set old_money = ?1,old_borrowdate=?2,monery=?3,borrow_date=?4,pay_data=?5,yanqi_state=1,lend_id=?7,borrow_id=?8 WHERE dan_id=?6")
	void yanqi(int oldMoney,Date old_borrowdate,String money,Date borrow_date,String pay_date,String dan_id,int lendId,int borrowId);
	
	@Modifying
	@Query(nativeQuery = true,value = "select * from dan")
	List<Dan> All();
	
	@Modifying
	@Query(nativeQuery = true,value = "update dan set borrow_id = ?2 where dan_id = ?1")
	void updatetime(String danId,int borrow_id);
}
