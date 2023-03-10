package com.itechart.javalab.firstproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
@Data
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
public class Photo {
    private long id;
    private String pathToFile;
    private String uuid;
}
