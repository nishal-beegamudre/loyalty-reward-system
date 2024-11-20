package com.transactionservice.keygenerator;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TopupKeyGenerator implements IdentifierGenerator {
	
	// This AtomicLong will serve as a thread-safe counter for key generation.
    private static AtomicLong counter = new AtomicLong(0);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Increment the counter and get the new value
        long nextValue = counter.incrementAndGet();
        Long timestamp = System.currentTimeMillis();

        // Format the key with the prefix "CUSTOM" and pad the number with leading zeros
        return String.format("TP"+timestamp.toString()+"%010d", nextValue);
    }

}
