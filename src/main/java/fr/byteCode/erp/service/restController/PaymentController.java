package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.PaymentDto;
import fr.byteCode.erp.persistance.entities.Payment;
import fr.byteCode.erp.service.services.ImplService.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;


    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @GetMapping(value = "/payment")
    public List<Payment> findAll() {
        return paymentService.findAll();
    }
    @GetMapping(value = "/{id}")
    public PaymentDto getPayment(@PathVariable Long id) {
        return paymentService.findById(id);
    }
    @PostMapping()
    public PaymentDto save(@RequestBody @Valid PaymentDto paymentDto) {
        return paymentService.save(paymentDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        paymentService.delete(id);
        return true;
    }
}
