package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends BaseRepository<Product,Long> {
    int getQuantityOfProductByCategoryIdAndSiteStockId(@Param("ca_id") Long ca_id,@Param("ss_id") Long ss_id);
    @Query(value = "select * from t_product where ss_id= :ss_id and pr_is_deleted= FALSE",nativeQuery = true)
    List<Product> getAllProductBySiteStockId(@Param("ss_id") Long ss_id);
    @Query(value = "select * from t_product where ca_id= :ca_id and pr_is_deleted= FALSE",nativeQuery = true)
    List<Product> getAllProductByCategoryId(@Param("ca_id") Long ca_id);
    @Query(value = "select * from t_product where pr_active= :pr_active and pr_is_deleted= FALSE",nativeQuery = true)
    List<Product> getAllProductByActive(@Param("pr_active") boolean pr_active);
    @Query(value = "select * from t_product where pr_state= :pr_state and pr_is_deleted= FALSE",nativeQuery = true)
    List<Product> getAllProductByState(@Param("pr_state") boolean pr_state);
    @Query(value = "select * from t_product where  ca_id= :ca_id and ss_id= :ss_id and pr_is_deleted= FALSE and pr_active= TRUE",nativeQuery = true)
    List<Product> getAllProductByCategoryIdAndSiteStockId(@Param("ca_id") Long ca_id,@Param("ss_id") Long ss_id);
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and pr_id= :pr_id and ca_id= :ca_id and ss_id= :ss_id",nativeQuery = true)
    int getQuantityOfProductByIdAndCaegoryIdAndSiteStockId(@Param("pr_id") Long pr_id,@Param("ca_id") Long ca_id,@Param("ss_id") Long ss_id);
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and pr_id= :pr_id and ca_id= :ca_id ",nativeQuery = true)
    int getQuantityOfProductByIdAndCaegoryId(@Param("pr_id") Long pr_id,@Param("ca_id") Long ca_id);
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and pr_id= :pr_id and ss_id= :ss_id",nativeQuery = true)
    int getQuantityOfProductByIdAndSiteStockId(@Param("pr_id") Long pr_id,@Param("ss_id") Long ss_id);
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and ss_id= :ss_id",nativeQuery = true)
    int getQuantityOfProductBySiteStockId(@Param("ss_id") Long ss_id);
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and  ca_id= :ca_id",nativeQuery = true)
    int getQuantityOfProductByCategoryId(@Param("ca_id") Long ca_id);
    @Query(value = "select count(*) from t_site_stock where ss_is_deleted= FALSE"  ,nativeQuery = true)
    int getQuantityOfSiteStock();
    @Query(value = "select count(*) from t_site_stock where  ss_state= TRUE and ss_is_deleted= FALSE"  ,nativeQuery = true)
    int getQuantityOfSiteStockActive();
    @Query(value = "select count(*) from t_site_stock where  ss_state= FALSE and ss_is_deleted= FALSE"  ,nativeQuery = true)
    int getQuantityOfSiteStockNonActive();
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and ss_id= :ss_id "  ,nativeQuery = true)
    int getQuantityOfProductbySiteStock(@Param("ss_id") Long ss_id);
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE",nativeQuery = true)
    float getNumberOfProduct();

    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and pr_state= TRUE",nativeQuery = true)
    float getNumberOfProductPromo();
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and pr_state= FALSE",nativeQuery = true)
    float getNumberOfProductNonPromo();
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and pr_active= TRUE",nativeQuery = true)
    float getNumberOfProductActive();
    @Query(value = "select count(*) from t_product where pr_is_deleted= FALSE and pr_state= FALSE",nativeQuery = true)
    float getNumberOfProductNonActive();
    @Query(value = "select count(*) from t_request_transfert where rt_is_deleted= FALSE ",nativeQuery = true)
    float getNumberOfRequestTransfert();
    @Query(value = "select count(*) from t_request_transfert where rt_is_deleted= FALSE and state= 0 ",nativeQuery = true)
    float getNumberOfRequestTransfertEmpty();
    @Query(value = "select count(*) from t_request_transfert where rt_is_deleted= FALSE and state= 1 ",nativeQuery = true)
    float getNumberOfRequestTransfertEnAttente();
    @Query(value = "select count(*) from t_request_transfert where rt_is_deleted= FALSE and state= 2 ",nativeQuery = true)
    float getNumberOfRequestTransfertEnAccepted();
    @Query(value = "select count(*) from t_request_transfert where rt_is_deleted= FALSE and state= 3 ",nativeQuery = true)
    float getNumberOfRequestTransfertDelivred();
    @Query(value = "select count(*) from t_request_transfert where rt_is_deleted= FALSE and u_id= :u_id ",nativeQuery = true)
    float getNumberOfRequesByInventoryManager(@Param("u_id") Long u_id);
    @Query(value = "select count(*) from t_user_groups where  groups_gr_id= 2 ",nativeQuery = true)
    float getNumberAdmin();
    @Query(value = "select count(*) from t_user_groups where  groups_gr_id= 3 ",nativeQuery = true)
    float getNumberClient();
    @Query(value = "select count(*) from t_user_groups where  groups_gr_id= 4 ",nativeQuery = true)
    float getNumberInventaryManager();
    @Query(value = "select count(*) from t_user_groups where  groups_gr_id= 5 ",nativeQuery = true)
    float getNumberPurchasingManager();
    @Query(value = "select count(*) from t_user_groups where  groups_gr_id= 6 ",nativeQuery = true)
    float getNumberDeliveryManager();
    @Query(value = "select count(*) from t_vehicule where  v_is_deleted= FALSE",nativeQuery = true)
    float getNumberVehicule();
    @Query(value = "select count(*) from t_vehicule where  v_is_deleted= FALSE and v_state= FALSE",nativeQuery = true)
    float getNumberVehiculeActive();
    @Query(value = "select count(*) from t_vehicule where  v_is_deleted= FALSE and v_state= TRUE",nativeQuery = true)
    float getNumberVehiculeNonActive();
    @Query(value = "select count(*) from t_salesorder where  so_is_deleted= FALSE",nativeQuery = true)
    float getNumberSales();
    @Query(value = "select count(*) from t_salesorder where  so_is_deleted= FALSE and client_id= :client_id",nativeQuery = true)
    float getNumberSalesByClientId(@Param("client_id")Long client_id);
    @Query(value = "select count(*) from t_salesorder where  so_is_deleted= FALSE and u_id= :u_id",nativeQuery = true)
    float getNumberSalesByPurchasingManagerId(@Param("u_id")Long u_id);
    @Query(value = "select count(*) from t_salesorder where  so_is_deleted= FALSE and u_id= :u_id and (so_date_created BETWEEN (:date1) and (:date2))",nativeQuery = true)
    float getNumberSalesByPurchasingManagerIdAndDate(@Param("u_id")Long u_id,@Param("date1")String date1,@Param("date2") String date2);
    @Query(value = "select count(*) from t_transfert_line_detail where tl_id= :tl_id",nativeQuery = true)
    float getNumberOfLineDemandeDetailByLineDemandeById(@Param("tl_id")Long tl_id);
    @Query(value = "select IFNULL(sum(total_sale),0) from t_salesorder where so_is_deleted= FALSE  and (so_date_created BETWEEN (:date1) and (:date2) )",nativeQuery = true)
    float getTotalVenteBy6Month(@Param("date1")String date1,@Param("date2") String date2 );
    @Query(value = "select count(*) from t_salesorder where so_is_deleted= FALSE  and (so_date_created BETWEEN (:date1) and (:date2) )",nativeQuery = true)
    float getNumberSalesByDay(@Param("date1")String date1,@Param("date2") String date2 );
    @Query(value = "SELECT count(*) FROM t_salesorder WHERE so_is_deleted= FALSE and  YEARWEEK(so_date_created, 1) = YEARWEEK(CURDATE(), 1)",nativeQuery = true)
    float getNumberSalesByCurrentWeek();
    @Query(value = "SELECT IFNULL(sum(total_sale),0) FROM t_salesorder WHERE so_is_deleted= FALSE and  YEARWEEK(so_date_created, 1) = YEARWEEK(CURDATE(), 1)",nativeQuery = true)
    float getTotalVenteByCurrentWeek();
    @Query(value = "select count(*) from t_salesorder WHERE so_is_deleted= FALSE and MONTH(so_date_created)=MONTH(curdate())",nativeQuery = true)
    float getNumberSalesByCurrentMonth();
    @Query(value = "select sum(total_sale) from t_salesorder where so_is_deleted= FALSE and MONTH(so_date_created)=MONTH(curdate())",nativeQuery = true)
    float getTotalVenteByCurrentMonth();
    @Query(value = "select count(*) from t_category WHERE ca_is_deleted= FALSE ",nativeQuery = true)
    float getNumberCategory();
    @Query(value = "select count(*) from t_brand WHERE b_is_deleted= FALSE ",nativeQuery = true)
    float getNumberBrand();
}

