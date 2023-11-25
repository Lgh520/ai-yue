package com.project.aiyue.dao.bo;

import lombok.Data;

@Data

public class BookRentWrapper {
    private String bookName;
    private Long bookId;
    private Boolean rentSuccess;
}
