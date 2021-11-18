package dev.davivieira.topologyinventory.bootstrap.samples;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class SampleObject {
    @NotBlank(message = "The field cannot be empty")
    public String field;

    @Min(message = "The minimum value is 10", value = 10)
    public int value;
}
