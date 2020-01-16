# bookstore-service
## Overview
The bookstore buys books in particular genres from people and sells them at marked-up prices. For the sake of simplicity, 
each Book has one Author and belongs to one Genre. The bookstore specializes in particular Genres, so the Genres are 
populated first (thus, the optional relationship with Book). The price of a Book is determined by its number of pages and 
the pricing factor of the Genre to which it belongs. Some Genres may be considered to be more valuable or rare than others; 
its pricing factor reflects this.

People offer to sell their books to the bookstore. The bookstore will determine whether a Book’s genre is one of the Genres 
it specializes in. If so, the selling price of the Book is calculated and an Offer is made to the seller that is equal to the 
Book’s calculated selling price minus the amount needed to achieve the bookstore’s desired profit margin. The seller can
accept the Offer, which results in the book being added to the bookstore's inventory, or decline the Offer.

## The Bookstore Service
The process of generating offers for Books people want to sell to the bookstore is to be automated. The bookstore service 
will also provide the ability to search for Books by title, Author and Genre, to search for Authors by name and to browse 
the available Genres.