package com.finders.Finders.Controllers;

import com.finders.Finders.Models.APIResponse;
import com.finders.Finders.Models.Service;
import com.finders.Finders.Repositories.ServiceJpaRepository;
import org.apache.juli.logging.Log;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/services")
public class ServiceRestController {
    @Autowired
    private ServiceJpaRepository serviceJpaRepository;

    @GetMapping("")
    public APIResponse<List<Service>> getServices(){
        try {
            List<Service> services = serviceJpaRepository.findAll();
            if (services.size() > 0) {
                return new APIResponse<>(HttpStatus.OK.value(), "servizi recuperati correttamente", services);
            } else {
                return new APIResponse<>(HttpStatus.NO_CONTENT.value(), "tabella dei Servizi vuota", services);
            }
        }catch (Exception e){
            System.out.println("Eccezione metodo getServices : "+e.getMessage());
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "eccezione nel metodo getServices, vedi console per dettagli", null);
        }
    }

    @GetMapping("/{id}")
    public APIResponse<Service> getService(@PathVariable Long id){
        try{
            Service service = serviceJpaRepository.getReferenceById(id);
            if(service.getId()!= null){
                return new APIResponse<>(HttpStatus.OK.value(), "servizio recuperato correttamente", service);
            }
            else
            {
                return new APIResponse<>(HttpStatus.NOT_FOUND.value(), "servizio non trovato", service);
            }
        }
        catch (Exception e)
        {
            System.out.println("Eccezione metodo getService : "+e.getMessage());
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "eccezione nel metodo getService, probabilmente il servizio non esiste. Vedi console per dettagli", null);
        }
    }

    @PostMapping("")
    public APIResponse<Service> createService(@RequestBody Service service){
        try{
             serviceJpaRepository.save(service);
             return new APIResponse(HttpStatus.CREATED.value(), "Servizio inserito correttamente nel db", service);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "errore nell'inserimento del servizio nel db" , null);
        }
    }

    @PutMapping("/{id}")
    public APIResponse<Service> updateService(@RequestBody Service service,@PathVariable Long id){
        try {
            Service s = serviceJpaRepository.getReferenceById(id);
            s.setName(service.getName());
            s.setCategory(service.getCategory());
            s.setPosition(service.getPosition());
            s.setCost(service.getCost());
            serviceJpaRepository.save(s);
            return new APIResponse(HttpStatus.OK.value(), "Servizio modificato correttamente", s);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "errore nella modifica del servizio" , null);
        }
    }

    @DeleteMapping("/{id}")
    public APIResponse<Boolean> deleteService(@PathVariable Long id){
            try {
                serviceJpaRepository.deleteById(id);
                return new APIResponse<>(HttpStatus.OK.value(), "Servizio eliminato correttamente", true);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "errore nell'eliminazione del servizio" , false);
            }
        }
}

