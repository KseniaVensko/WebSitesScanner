package com.kseniavensko.HeaderValidators;

import com.kseniavensko.ValidationResult;

public interface IHeaderValidator {

   // boolean accept(String headerName);
    ValidationResult validate();
}
