package com.project.aiyue.bo;

import lombok.Data;

@Data

public class BookRentWrapper {
    private String bookName;
    private Long bookId;
    private Boolean rentSuccess;
}
