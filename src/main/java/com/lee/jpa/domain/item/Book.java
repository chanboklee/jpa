package com.lee.jpa.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue(value = "B")
@Entity
public class Book extends Item{

    private String author;
    private String isbn;

    public Book(String name, int price, int stockQuantity){
        super(name, price, stockQuantity);
    }
}
