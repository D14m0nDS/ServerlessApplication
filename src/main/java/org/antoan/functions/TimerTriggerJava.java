package org.antoan.functions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Function with Timer Trigger.
 */
public class TimerTriggerJava {

    /**
     * This function will be invoked periodically according to the specified schedule.
     */
    @FunctionName("calculateAverageRatings")
    public void run(
            @TimerTrigger(name = "calculateAverageRatingsTrigger", schedule = "0 30 11 * * *") String timerInfo,
            final ExecutionContext context) {

        context.getLogger().info("Java Timer trigger function executed at: " + java.time.LocalDateTime.now());

        // TODO: Add code to fetch ratings, calculate averages, and update the database
        // calculateAndStoreAverageRatings();
    }
}
