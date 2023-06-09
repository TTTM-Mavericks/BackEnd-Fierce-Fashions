package com.ff.entity;

import com.ff.entity.enum_pkg.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String description;
    private Double price;
    private Long quantity;

    @Lob
    @Column(columnDefinition = "oid")
    private byte[] image;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    private Date updatedDate;

    @Enumerated(EnumType.STRING)
    private Status status_product;
    private Double rate;

    @OneToMany(mappedBy = "product_comment")
    List<CommentProductEntity> comments;

    @ManyToMany(mappedBy = "product_cate")
    List<CategoryEntity> categoryList;

    @OneToMany(mappedBy = "product_detail")
    List<OrderDetailsEntity> details;

    public ProductEntity(String name, String title, String description, Double price, Long quantity, byte[] image, Date createdDate, Date updatedDate, Double rate) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.rate = rate;
        this.status_product=Status.ACTIVE;
    }

    public ProductEntity(String name, String title, String description, Double price, Long quantity, byte[] image, Date createdDate, Date updatedDate, Status status_product, Double rate, List<CategoryEntity> categoryList) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status_product = status_product;
        this.rate = rate;
        this.categoryList = categoryList;
    }

    //    public ProductEntity(String name, String title, String description, Double price, Long quantity, byte[] image, Date createdDate, Date updatedDate, Double rate) {
//        this.name = name;
//        this.title = title;
//        this.description = description;
//        this.price = price;
//        this.quantity = quantity;
//        this.image = image;
//        this.createdDate = createdDate;
//        this.updatedDate = updatedDate;
//        this.rate = rate;
//    }
}

