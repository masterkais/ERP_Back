
package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dao.CategoryDao;
import fr.byteCode.erp.persistance.dao.ProductDao;
import fr.byteCode.erp.persistance.dao.RequestTransfertDao;
import fr.byteCode.erp.persistance.dao.SiteStockDao;
import fr.byteCode.erp.persistance.entities.Category;
import fr.byteCode.erp.persistance.entities.RequestTransfert;
import fr.byteCode.erp.persistance.entities.SiteStock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/statistique")
public class StatistiqueController {
    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final SiteStockDao siteStockDao;
    private RequestTransfertDao requestTransfertDao;
    public StatistiqueController(ProductDao productDao, RequestTransfertDao requestTransfertDao,CategoryDao categoryDao,SiteStockDao siteStockDao){
        this.productDao=productDao;
        this.requestTransfertDao=requestTransfertDao;
        this.categoryDao=categoryDao;
        this.siteStockDao=siteStockDao;
    }
    @GetMapping(value = "/numberSiteStock")
    public float getNumberSiteStock() {
        return productDao.getQuantityOfSiteStock();
    }
    @GetMapping(value = "/numberSiteStockDisponible")
    public float getNumberSiteStockActive() {
        return productDao.getQuantityOfSiteStockActive();
    }
    @GetMapping(value = "/numberSiteStockNonDisponible")
    public float getNumberSiteStockNonActive() {
        return productDao.getQuantityOfSiteStockNonActive();
    }
    @GetMapping(value = "/numberProductBySiteSock/{ids}")
    public float getNumberProductBySiteStock(@PathVariable Long ids) {
        return productDao.getQuantityOfProductbySiteStock(ids);
    }
    @GetMapping(value = "/numberProduct")
    public float getNumberProduct() {
        return productDao.getNumberOfProduct();
    }
    @GetMapping(value = "/numberProductPromo")
    public float getNumberProductPromo() {
        return productDao.getNumberOfProductPromo();
    }
    @GetMapping(value = "/numberProductNomPromo")
    public float getNumberProductNomPromo() {
        return productDao.getNumberOfProductNonPromo();
    }
    @GetMapping(value = "/numberProductActive")
    public float getNumberProductActive() {
        return productDao.getNumberOfProductActive();
    }
    @GetMapping(value = "/numberProductNonActive")
    public float getNumberProductNonActive() {
        return productDao.getNumberOfProductNonActive();
    }
    @GetMapping(value = "/numberRequestTransfert")
    public float getNumberRequestTransfert() {
        return productDao.getNumberOfRequestTransfert();
    }
    @GetMapping(value = "/numberRequestTransfertEmpty")
    public float getNumberRequestTransfertEmpty() {
        return productDao.getNumberOfRequestTransfertEmpty();
    }
    @GetMapping(value = "/numberRequestTransfertAttente")
    public float getNumberRequestTransfertEnAttente() {
        return productDao.getNumberOfRequestTransfertEnAttente();
    }
    @GetMapping(value = "/numberRequestTransfertAccepted")
    public float getNumberRequestTransfertAccepted() {
        return productDao.getNumberOfRequestTransfertEnAccepted();
    }
    @GetMapping(value = "/numberRequestTransfertDelyvred")
    public float getNumberRequestTransfertDelivery() {
        return productDao.getNumberOfRequestTransfertDelivred();
    }
    @GetMapping(value = "/numberRequestTransfertByInventoryManager/{id}")
    public float getNumberRequestTransfertByInvetoryManager(@PathVariable Long id) {
        return productDao.getNumberOfRequesByInventoryManager(id);
    }
    @PostMapping(value = "/numberRequestTransfertByDate")
    public float getNumberRequestTransfertByDate(@RequestParam String date) {
        String str = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        List<RequestTransfert> requestTransferts=requestTransfertDao.getAllByDateCreated(dateTime);
        return requestTransferts.size();
    }
    @GetMapping(value = "/numberVehicule")
    public float getNumberVehicules() {
        return productDao.getNumberVehicule();
    }
    @GetMapping(value = "/numberVehiculeDisponible")
    public float getNumberVehiculesNonActive() {
        return productDao.getNumberVehiculeNonActive();
    }
    @GetMapping(value = "/numberVehiculeNonDisponible")
    public float getNumberVehiculesActive() {
        return productDao.getNumberVehiculeActive();
    }
    @GetMapping(value = "/numberClient")
    public float getNumberClient() {
        return productDao.getNumberClient();
    }
    @GetMapping(value = "/numbeDelivery")
    public float getNumberDelivery() {
        return productDao.getNumberDeliveryManager();
    }
    @GetMapping(value = "/numberInvetoryManager")
    public float getNumberInventoryManager() {
        return productDao.getNumberInventaryManager();
    }
    @GetMapping(value = "/PurchasingManager")
    public float getNumberPurchasingManager() {
        return productDao.getNumberPurchasingManager();
    }
    @GetMapping(value = "/numberAdmin")
    public float getNumberAdmin() {
        return productDao.getNumberAdmin();
    }
    @GetMapping(value = "/salesOrder")
    public float getNumberSalesOrder() {
        return productDao.getNumberSales();
    }
    @GetMapping(value = "/salesOrderByClient/{id}")
    public float getNumberSalesOrderByClient(@PathVariable Long id) {
        return productDao.getNumberSalesByClientId(id);
    }
    @GetMapping(value = "/salesByPrchasingManager/{id}")
    public float getNumberSalesOrderByPurchasingManager(@PathVariable Long id) {
        return productDao.getNumberSalesByPurchasingManagerId(id);
    }
    @GetMapping(value = "/salesByPrchasingManagerAndDate/{id}")
    public float getNumberSalesOrderByPurchasingManagerAndDate(@PathVariable Long id) {
        LocalDateTime localDT = LocalDateTime.now();
        float total=productDao.getNumberSalesByPurchasingManagerIdAndDate(id,DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00").format(localDT)+"",DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59").format(localDT)+"");
        return total;
    }
    @GetMapping(value = "/getTotalVenteBy6MonthS1")
    public List<Float> getTotalVenteBetweenTowDateS1() throws JSONException {
        float total2017S1=productDao.getTotalVenteBy6Month("2017-01-01","2017-06-31");
        float total2018S1=productDao.getTotalVenteBy6Month("2018-01-01","2018-06-31");
        float total2019S1=productDao.getTotalVenteBy6Month("2019-01-01","2019-06-31");
        float total2020S1=productDao.getTotalVenteBy6Month("2020-01-01","2020-06-31");
        float total2021S1=productDao.getTotalVenteBy6Month("2021-01-01","2021-06-31");
        float total2022S1=productDao.getTotalVenteBy6Month("2022-01-01","2022-06-31");
        JSONArray array = new JSONArray();
        JSONObject item1 = new JSONObject();
        List<Float> ls=new ArrayList<>();
        ls.add(total2017S1);
        ls.add(total2018S1);
        ls.add(total2019S1);
        ls.add(total2020S1);
        ls.add(total2021S1);
        ls.add(total2022S1);
        item1.put("data",ls);
        item1.put("label", "Series A");
        array.put(item1);
        return ls;
    }
    @GetMapping(value = "/getTotalVenteBy6MonthS2")
    public List<Float> getTotalVenteBetweenTowDateS2() throws JSONException {
        float total2017S2=productDao.getTotalVenteBy6Month("2017-07-01","2017-12-31");
        float total2018S2=productDao.getTotalVenteBy6Month("2018-07-01","2018-12-31");
        float total2019S2=productDao.getTotalVenteBy6Month("2019-07-01","2019-12-31");
        float total2020S2=productDao.getTotalVenteBy6Month("2020-07-01","2020-12-31");
        float total2021S2=productDao.getTotalVenteBy6Month("2021-07-01","2021-12-31");
        float total2022S2=productDao.getTotalVenteBy6Month("2022-07-01","2022-12-31");
        JSONArray array = new JSONArray();
        JSONObject item1 = new JSONObject();
        List<Float> ls=new ArrayList<>();
        ls.add(total2017S2);
        ls.add(total2018S2);
        ls.add(total2019S2);
        ls.add(total2020S2);
        ls.add(total2021S2);
        ls.add(total2022S2);
        item1.put("data",ls);
        item1.put("label", "Series A");
        array.put(item1);
        return ls;
    }
    @GetMapping(value = "/getNumberProductsByCategorieV2")
    public String getNumberProductsByStockCategorie() throws JSONException {
        List<Category> categorys=categoryDao.findAll();
        JSONArray array = new JSONArray();
        List<Float> ls=new ArrayList<>();
        for(int i=0;i<categorys.size();i++){
            JSONObject item1 = new JSONObject();
            item1.put(categorys.get(i).getName(),productDao.getQuantityOfProductbySiteStock(categorys.get(i).getId()));
            array.put(item1);
        }
        return array.toString();
    }
    @GetMapping(value = "/getNumberProductsByStockV2")
    public String getNumberProductsByStock() throws JSONException {
        List<SiteStock> siteStocks=siteStockDao.findAll();
        JSONArray array = new JSONArray();
        List<Float> ls=new ArrayList<>();
        for(int i=0;i<siteStocks.size();i++){
            JSONObject item1 = new JSONObject();
            item1.put(siteStocks.get(i).getName(),productDao.getQuantityOfProductbySiteStock(siteStocks.get(i).getId()));
            array.put(item1);
        }
        return array.toString();
    }
    @GetMapping(value = "/getTotalVenteByCurrentDay")
    public float getTotalVenteByCurrentDay() throws JSONException {
        LocalDateTime localDT = LocalDateTime.now();

        float total=productDao.getTotalVenteBy6Month(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00").format(localDT)+"",DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59").format(localDT)+"");
        return total;

    }
    @GetMapping(value = "/getNumberCommandeByCurrentDay")
    public float getNumberCommandeByCurrentDay() throws JSONException {
        LocalDateTime localDT = LocalDateTime.now();
        float total=productDao.getNumberSalesByDay(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00").format(localDT)+"",DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59").format(localDT)+"");
        return total;
    }

    @GetMapping(value = "/getTotalVenteByCurrentWeek")
    public float getTotalVenteByCurrentWeek() throws JSONException {
        float total=productDao.getTotalVenteByCurrentWeek();
        return total;

    }
    @GetMapping(value = "/getNumberCommandeByCurrentWeek")
    public float getNumberCommandeByCurrentWeek() throws JSONException {
        LocalDateTime localDT = LocalDateTime.now();
        float total=productDao.getNumberSalesByCurrentWeek();
        return total;
    }

    @GetMapping(value = "/getTotalVenteByCurrentMonth")
    public float getTotalVenteByCurrentMonth() throws JSONException {
        LocalDateTime localDT = LocalDateTime.now();

        float total=productDao.getTotalVenteByCurrentMonth();
        return total;

    }
    @GetMapping(value = "/getNumberCommandeByCurrentMonth")
    public float getNumberCommandeByCurrentMonth() throws JSONException {
        LocalDateTime localDT = LocalDateTime.now();
        float total=productDao.getNumberSalesByCurrentMonth();
        return total;
    }

    @GetMapping(value = "/getNumberCategory")
    public float getNumberCategory() throws JSONException {
        float total=productDao.getNumberCategory();
        return total;
    }

    @GetMapping(value = "/getNumberBrand")
    public float getNumberBrand() throws JSONException {
        float total=productDao.getNumberBrand();
        return total;
    }
}
