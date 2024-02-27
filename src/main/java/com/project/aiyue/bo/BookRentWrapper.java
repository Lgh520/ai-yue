package com.project.aiyue.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookRentWrapper {
    private String bookName;
    private Long bookId;
    private Boolean rentSuccess;
    @JsonIgnore
    private Long rentId;
}
