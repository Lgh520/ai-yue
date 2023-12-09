package com.project.aiyue.bo;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data

public class BookRentWrapper {
    private String bookName;
    private Long bookId;
    private Boolean rentSuccess;
    @JsonIgnore
    private Long rentId;
}
