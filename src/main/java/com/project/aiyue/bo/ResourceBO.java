package com.project.aiyue.bo;

import lombok.Data;
import java.io.Serializable;

@Data
public class ResourceBO implements Serializable {
    private static final long serialVersionUID = 3817078348646177946L;
    private String algorithm;
    private String ciphertext;
    private String nonce;
}
