package com.bw.reference.cron;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@RequiredArgsConstructor
@Component
public class CronSchedule {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    //private final TransactionTemplate transactionTemplate;
    //private final ReferenceCron referenceCron;

    @Scheduled(cron = "0 */20 * * * ?")
    public void doReferenceCronStuff() {
        //referenceCron.doStuff();
    }
}
