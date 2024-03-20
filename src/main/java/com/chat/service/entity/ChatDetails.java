package com.chat.service.entity;


import com.chat.service.enums.MessageType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "chat_detail")
public class ChatDetails {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String content;

    @Column
    private String userName;

    @Column
    private MessageType type;

    @Column(name = "status")
    private boolean status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", insertable = false, updatable = false )
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
}
