package com.bw.reference.sequence;

import com.bw.reference.etc.SequenceGeneratorImpl;
import com.bw.reference.qualifier.PortalAccountCodeSequence;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Locale;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 */
@PortalAccountCodeSequence
@Named
class PortalAccountCodeSequenceGenerator extends SequenceGeneratorImpl {

    @Inject
    public PortalAccountCodeSequenceGenerator(EntityManager entityManager, TransactionTemplate transactionTemplate) {
        super(entityManager, transactionTemplate, "portal_account_id");
    }

    @Override
    public String getNext() {
        return String.format(Locale.ENGLISH, "ACCT%010d", getNextLong());
    }
}
