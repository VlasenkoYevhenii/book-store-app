package com.example.bookstoreapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@SQLDelete(sql = "UPDATE carts SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
@Table(name = "shopping_carts")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "ShoppingCart.withUserAndCartItemsAndBooks",
                attributeNodes = {
                        @NamedAttributeNode("user"),
                        @NamedAttributeNode(value = "cartItems", subgraph = "cartItemsGraph")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "cartItemsGraph",
                                attributeNodes = {
                                        @NamedAttributeNode("book")
                                }
                        )
                }
        )
})
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingCart", orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(nullable = false)
    private boolean isDeleted = false;
}
