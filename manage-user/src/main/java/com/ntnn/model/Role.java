package com.ntnn.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Role {
    private Integer id;
    private String name;
    private String description;
}
