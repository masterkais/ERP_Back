package fr.byteCode.erp.persistance.constants;

import fr.byteCode.erp.persistance.dto.DeliveryManDto;
import fr.byteCode.erp.persistance.entities.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;

public final class EntitiesContants {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static String dateNaissance = "2000-12-31 23:59";
    static String dateDebutContrat = "2019-01-01 00:00";
    static LocalDateTime daeNess = LocalDateTime.parse(dateNaissance, formatter);
    static LocalDateTime daeDebCont = LocalDateTime.parse(dateDebutContrat, formatter);
    public static final DeliveryManDto DELIVERY_MAN_DTO = null;
    public static final InventoryManager INVENTORY_MANAGER =null; //new InventoryManager(ONE, "naji", "naji", "Roue mhrza", "001435524", "naji2032@gmail.com", "sfax", "/d/img", true, daeNess, daeDebCont
          //  , false, null, null);
    public static final Ticket TICKET = new Ticket(ONE, "maintenance", "mahdy", "carte mere", daeNess, daeNess, "ouverte", false, null, INVENTORY_MANAGER);
    public static final RequestTransfert REQUEST_TRANSFERT = null;
    public static final Category CATEGORY = new Category(ONE, "tochiba", "ochiba version 3");
    public static final Product PRODUCT = new Product(ONE, "tv32", "tv 32 pouces", 1000, 1200, true, true);
    public static final TransfertLine TRANSFERT_LINE = null; //new TransfertLine(ONE, ELEVEN, "mega1", "mega2", false, null, REQUEST_TRANSFERT, PRODUCT);
    public static final ExitVoucher EXIT_VOUCHER = new ExitVoucher(ONE, daeDebCont, false, null, REQUEST_TRANSFERT);
    public static final EntryVoucher ENTRY_VOUCHER = new EntryVoucher(ONE, daeDebCont, false, null, REQUEST_TRANSFERT);

}
