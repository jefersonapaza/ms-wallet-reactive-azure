package main.java.nttdata.bootcamp.quarkus.service;
import com.azure.messaging.eventhubs.*;
import java.util.Arrays;
import java.util.List;
import com.azure.identity.*;

import io.smallrye.mutiny.Uni;
import org.bson.Document;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.util.List;
import java.lang.String;


String namespaceName = "semana3.servicebus.windows.net";
String eventHubName = "semana3";
    
      EventHubService() {

        // create a token using the default Azure credential        
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder()
                .authorityHost(AzureAuthorityHosts.AZURE_PUBLIC_CLOUD)
                .build();

        // create a producer client        
        EventHubProducerClient producer = new EventHubClientBuilder()        
            .fullyQualifiedNamespace(namespaceName)
            .eventHubName(eventHubName)
            .credential(credential)
            .buildProducerClient();

        // sample events in an array
        List<EventData> allEvents = Arrays.asList(new EventData("Foo"), new EventData("Bar"));

        // create a batch
        EventDataBatch eventDataBatch = producer.createBatch();

        for (EventData eventData : allEvents) {
            // try to add the event from the array to the batch
            if (!eventDataBatch.tryAdd(eventData)) {
                // if the batch is full, send it and then create a new batch
                producer.send(eventDataBatch);
                eventDataBatch = producer.createBatch();

                // Try to add that event that couldn't fit before.
                if (!eventDataBatch.tryAdd(eventData)) {
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                        + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }
        // send the last batch of remaining events
        if (eventDataBatch.getCount() > 0) {
            producer.send(eventDataBatch);
        }
        producer.close();
    }   
 


}
