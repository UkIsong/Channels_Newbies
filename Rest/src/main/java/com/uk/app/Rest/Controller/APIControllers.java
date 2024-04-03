package com.uk.app.Rest.Controller;
import com.uk.app.Rest.Connector.OfsUtility;
import com.uk.app.Rest.Models.Acct;
import com.uk.app.Rest.Models.RestServicesOfsRequest;
import com.uk.app.Rest.Models.RestServicesOfsResponse;
import com.uk.app.Rest.user.UserRepo;
import com.uk.app.Rest.Models.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class APIControllers {

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/")
    public String getPage() {
        return "Welcome To UKs World";
    }

    @GetMapping(value="/users")
    public List<user> getUsers(){
        return userRepo.findAll();
    }

    @PostMapping(value="/save")
    public String saveUser(@RequestBody user user){
        userRepo.save(user);
        return"Saved...";
    }

    @PutMapping(value="/update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody user user){
        user updatedUser = userRepo.findById(id).get();
        updatedUser.setFirstname(user.getFirstname());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setOccupation(user.getOccupation());
        updatedUser.setAge(user.getAge());
        userRepo.save(updatedUser);
        return "Updated...";
    }

    @DeleteMapping(value="/delete/{id}")
    public String deleteUser(@PathVariable long id){
        user deleteUser = userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        return "Deleted User with id: "+id;
    }

    @GetMapping(value="/account/details/{acctId}")
    public Acct getAcctDetails(@PathVariable String acctId){
        Acct response = new Acct();
        String filters = "ACCOUNT.NUMBER:EQ=".concat(acctId);
        String generatedOfsRequest = OfsUtility.genericOfsGenerator("ACCT.BAL2","INPUTT", "123456", filters, "GB0010001");
        log.info("generatedOfsRequest >> {}", generatedOfsRequest);
        RestServicesOfsResponse restServicesOfsResponse = TafjRestService(generatedOfsRequest);
        log.info("restServicesOfsResponse >> {}", restServicesOfsResponse);
        if (restServicesOfsResponse == null) {
            response.setResponseCode("99");
            response.setResponseDescription("Unable to get response from t24");
            return response;
        }
        List<List<Optional<String>>> result = OfsUtility.genericOfsDataExtraction(restServicesOfsResponse.getOfsResponse());
        List<Optional<String>> headers = result.get(0);
        List<Optional<String>> get = result.get(1);
        if (((Optional)get.get(0)).toString().contains("No records were found that matched the selection criteria") || ((Optional)get.get(0)).toString().contains("No Accounts to display") || ((Optional)get.get(0)).toString().contains("Account Number RECORD NOT FOUND invalid") || get.toString().contains("Account Number RECORD NOT FOUND invalid")) {
            response.setResponseCode("64");
            response.setResponseDescription("No records were found for Account_Number " + acctId);
            return response;
        }
        String availableBalance = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Cleared Bal")))).orElse("")).replace("\"", "").trim();
        String limitRef = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Limit Ref")))).orElse("")).replace("\"", "").trim();
        String lockedAmount = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Locked Amount")))).orElse("")).replace("\"", "").trim();
        String useableBalance = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Useable Bal")))).orElse("")).replace("\"", "").trim();
        String currency = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Ccy")))).orElse("")).replace("\"", "").trim();
        String acctName = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Name")))).orElse("")).replace("\"", "").trim();
        String acctNo = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Acct No")))).orElse("")).replace("\"", "").trim();
        String product = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Product")))).orElse("")).replace("\"", "").trim();
        String workingBal = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Working Bal")))).orElse("")).replace("\"", "").trim();
        String ledgerBal = ((String)((Optional<String>)get.get(headers.indexOf(Optional.ofNullable("Ledger Bal")))).orElse("")).replace("\"", "").trim();

        response.setAcctNo(acctNo);
        response.setCcy(currency);
        response.setName(acctName);
        response.setProduct(product);
        response.setClearedBal(availableBalance);
        response.setLedgerBal(ledgerBal);
        response.setLimitRef(limitRef);
        response.setLockedAmount(lockedAmount);
        response.setWorkingBal(workingBal);
        response.setResponseCode("00");
        response.setResponseDescription("Successful");
        return response;
    }


    private RestServicesOfsResponse TafjRestService(String generateOfsRequest) {
        RestServicesOfsResponse restServicesOfsResponse = new RestServicesOfsResponse();
        RestServicesOfsRequest restServicesOfsRequest = new RestServicesOfsRequest();
        log.info("generateOfsRequest >>> {}", generateOfsRequest);
        restServicesOfsRequest.setOfsRequest(generateOfsRequest);

        String url = "http://" + "127.0.0.1" + ":"+"8085"+"/TAFJRestServices/resources/ofs";
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBasicAuth("tafj", "tafj");
        HttpEntity<RestServicesOfsRequest> entity = new HttpEntity(restServicesOfsRequest, (MultiValueMap)headers1);
        try {
            RestTemplate restTemplate = new RestTemplate();
            log.info("Step 1");
            restServicesOfsResponse = (RestServicesOfsResponse)restTemplate.exchange(url, HttpMethod.POST, entity, RestServicesOfsResponse.class, new Object[0]).getBody();
            log.info("Success Response >>> {}", restServicesOfsResponse.toString());
        } catch (Exception e) {
            restServicesOfsResponse.setOfsRequest(generateOfsRequest);
            log.info("Error Message Stage 3");
            restServicesOfsResponse.setOfsResponse("," + e.getMessage());
        }
        return restServicesOfsResponse;
    }
}
