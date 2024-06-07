package org.deblock.exercise.validator;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.function.Predicate;

@Component
public class Validator {

    public static boolean isReturnDateBeforeDepartureDate(LocalDate departureDate, LocalDate returnDate){
        return returnDate.isBefore(departureDate);
    }

    public static boolean isReturnDateBeforeDepartureDate(OffsetDateTime departureDateTime, OffsetDateTime returnDateTime){
        return returnDateTime.isBefore(departureDateTime);
    }

}
