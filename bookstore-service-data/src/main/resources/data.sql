insert into Author values (1, 'Kim', 'Stanley', 'Robinson');
insert into Author values (2, 'Isaac', null, 'Asimov');
insert into Author values (3, 'Edgar', 'Allan', 'Poe');

insert into Genre values (1, 'Science Fiction', 1.1);
insert into Genre values (2, 'Fiction', 1.0);

insert into Book (id, Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values (1, 'Red Mars', 1, 'Spectra', 1993, '978-0553560732', 572, 12.58, 1);
insert into Book (id, Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values (2, 'Green Mars', 1, 'Spectra', 1995, '978-0553572391', 624, 13.73, 1);
insert into Book (id, Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values (3, 'Blue Mars', 1, 'Spectra', 1997, '978-0553573350', 784, 17.25, 1);
insert into Book (id, Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values (4, 'Foundation', 2, 'Spectra', 2004, '978-0553568910', 255, 5.61, 1);
insert into Book (id, Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values (5, 'Foundation and Empire', 2, 'Spectra', 2004, '978-0553293370', 320, 7.04, 1);
insert into Book (id, Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values (6, 'Second Foundation', 2, 'Spectra', 2004, '978-0553293362', 304, 6.69, 1);
insert into Book (id, Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values (7, 'The Tales of Edgar Allan Poe', 3, 'Independently published', 2019, '978-1686604515', 223, 4.46, 2);
