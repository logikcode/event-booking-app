package com.bw.reference.sequence;

import com.bw.reference.etc.SequenceGeneratorImpl;
import com.bw.reference.qualifier.ResourceCodeSequence;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Locale;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/

@ResourceCodeSequence
@Named
public class ResourceCodeSequenceGenerator extends SequenceGeneratorImpl {

    @Inject
    public ResourceCodeSequenceGenerator(EntityManager entityManager, TransactionTemplate transactionTemplate) {
        super(entityManager, transactionTemplate, "resource_code");
    }

    @Override
    public String getNext() {
        return String.format(Locale.ENGLISH, "R%09d", getNextLong());
    }
}
