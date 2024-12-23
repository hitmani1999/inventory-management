package org.eshopping.steps;

import io.cucumber.java.en.Given;
import org.eshopping.model.Inventory;
import org.eshopping.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EShoppingSteps {

    private static final Logger logger = LoggerFactory.getLogger(EShoppingSteps.class);

//    @Autowired
//    private InventoryService inventoryService;

    @Given("product id")
    public void getProductDetailsByIdTest(){
        logger.info("Test started..");
    }
}
