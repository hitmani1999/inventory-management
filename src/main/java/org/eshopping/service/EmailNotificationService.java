package org.eshopping.service;

import org.eshopping.model.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements INotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    @Override
    @Async
    public void sendNotification(Inventory inventory) {
        logger.info("Notification send for product id {}", inventory.getId());
    }
}
